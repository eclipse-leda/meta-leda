# /********************************************************************************
# * Copyright (c) 2022 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************/

# Define defaults, which can be overridden within the recipe
export CONTAINER_ARCH

CONTAINER_OS ??= "linux"
SDV_DL_FILE ??= "${DL_DIR}/${PN}-${PV}.oci"

K3S_AGENT_PRELOAD_DIR ??= "/var/lib/rancher/k3s/agent/images"

# Documentation of configuration variables
CONTAINER_ARCH[doc] = "Specify the container machine architecture, e.g. amd64, arm64"
CONTAINER_OS[doc] = "Specify the container operatin system, e.g. linux"
SDV_DL_FILE[doc] = "Specify how the archive is downloaded"

do_fetch_container[depends] += "skopeo-native:do_populate_sysroot"
do_fetch_container[depends] += "jq-native:do_populate_sysroot"

# Refetch containers every time for now during development
do_compile[noexec] = "1"
do_fetch_container[nostamp] = "1"
do_unpack_container[nostamp] = "1"

do_fetch_container() {   
    SKOPEO_LOC=$(PATH=/usr/bin:${PATH} whereis skopeo)
    bbnote "Skopeo Location: ${SKOPEO_LOC}"

    JQ_LOC=$(PATH=/usr/bin:${PATH} whereis jq)
    bbnote "jq location: ${JQ_LOC}"

    bbnote "Container Image Ref: ${SDV_IMAGE_REF}"
    CONTAINER_REGISTRY=""
    if printf '%s\n' "${SDV_IMAGE_REF}" | grep -Fqe "/"
    then
        CONTAINER_REGISTRY="$(echo "${SDV_IMAGE_REF}" | cut -f1 -d'/')"
    else
        CONTAINER_REGISTRY="docker.io"
    fi
    bbnote "Container Registry: ${CONTAINER_REGISTRY}"
    if ! PATH=/usr/bin:${PATH} skopeo login --authfile ~/auth.json ${CONTAINER_REGISTRY} ;
    then
        bbwarn "Not logged into ${CONTAINER_REGISTRY}, download of container image ${SDV_IMAGE_REF} may fail!"
    fi

    if [ -z "$CONTAINER_ARCH" ]
    then
      case ${TARGET_ARCH} in
          aarch64*)    CONTAINER_ARCH_AUTODETECT="arm64" ;;
          arm*)        CONTAINER_ARCH_AUTODETECT="arm" ;;
          x86_64*)     CONTAINER_ARCH_AUTODETECT="amd64" ;;
          *)           bbfatal "Recipe has no target-arch-to-container-arch mapping for '${TARGET_ARCH}'. Please explicitly set CONTAINER_ARCH in recipe."
      esac
      bbnote "Auto-detect container architecture based on target architecture (${TARGET_ARCH}): $CONTAINER_ARCH_AUTODETECT"
      export CONTAINER_ARCH=$CONTAINER_ARCH_AUTODETECT
    else
      bbnote "Container architecture override configured in recipe: $CONTAINER_ARCH"
    fi

    bbnote "Fetching container image: ${SDV_IMAGE_REF}"
    bbnote "Target container architecture: ${CONTAINER_ARCH}"
    bbnote "Target container operating system: ${CONTAINER_OS}"
    bbnote "Storing to: ${SDV_DL_FILE}"

    # Bugfix: Redownload if container has zero size (skopeo creates empty files on error)
    if [ -f ${SDV_DL_FILE} -a ! -s ${SDV_DL_FILE} ];
    then
        bbwarn "Deleting zero-size container image file: ${SDV_DL_FILE}"
        rm ${SDV_DL_FILE}
    fi

    if [ ! -f ${SDV_DL_FILE} ];
    then
        if ! PATH=/usr/bin:${PATH} \
            skopeo \
            --debug \
            --override-arch ${CONTAINER_ARCH} \
            --override-os ${CONTAINER_OS} \
            copy \
            --authfile ~/auth.json \
            --additional-tag ${SDV_IMAGE_REF}:${SDV_IMAGE_TAG} \
            docker://${SDV_IMAGE_REF}:${SDV_IMAGE_TAG} \
            docker-archive:${SDV_DL_FILE}:${SDV_IMAGE_REF} ;
        then
            RC_SKOPEO=$?
            bbwarn "Error copying container image. rc=${RC_SKOPEO}"
        else 
            RC_SKOPEO=$?
            bbnote "Stored container. rc=${RC_SKOPEO}"
        fi
    fi

    # Sanity check of downloaded file
    if [ ! -f ${SDV_DL_FILE} ];
    then
        bbwarn "Unable to find expected downloaded file: ${SDV_DL_FILE}"
    fi

    if [ ! -s ${SDV_DL_FILE} ];
    then
        bbwarn "Downloaded container has zero size: ${SDV_DL_FILE}"
    fi

    TAGS_IN_TAR=$(tar -xOf ${SDV_DL_FILE} manifest.json | jq -r .[0].RepoTags[])
    if ! echo "${TAGS_IN_TAR}" | grep -q "${SDV_IMAGE_REF}:${SDV_IMAGE_TAG}" ;
    then
        bbwarn "Container image is missing expected tag: ${SDV_IMAGE_REF}:${SDV_IMAGE_TAG}"
        bbwarn "Container image contains the following tags: ${TAGS_IN_TAR}"
        CONTAINER_MANIFEST=$(tar -xOf ${SDV_DL_FILE} manifest.json)
        bbwarn "Container image manifest: ${CONTAINER_MANIFEST}"
    fi

    exit 0
}

do_unpack_container() {
    CONTAINER_SOURCE_FOLDER="${S}/container-image"
    bbnote "Copying ${SDV_DL_FILE} to ${CONTAINER_SOURCE_FOLDER}"
    cp ${SDV_DL_FILE} ${CONTAINER_SOURCE_FOLDER}
}

# Todo: Move the layer blobs into the containerd storage
do_install() {
    install -d ${D}${K3S_AGENT_PRELOAD_DIR}
    cp -R --no-dereference --preserve=mode,links -v ${S}/container-image ${D}${K3S_AGENT_PRELOAD_DIR}/${PN}.tar
}

addtask do_fetch_container before do_unpack_container after do_fetch 
addtask do_unpack_container before do_install after do_fetch_container 

FILES:${PN} += "${K3S_AGENT_PRELOAD_DIR}/${PN}.tar"

PACKAGES = "${PN}"
