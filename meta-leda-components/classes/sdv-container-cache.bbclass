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
export SKOPEO_AUTH_PARAMETER

CONTAINER_OS ??= "linux"
CONTAINER_REGISTRY_REQUIRES_AUTH ??= "0"
CONTAINER_SKIP_MISSING_ARCH ??= "0"
SDV_DL_FILENAME ??= "${PN}-${PV}-${TARGET_ARCH}-${SDV_IMAGE_TAG}.tar"
SDV_DL_FILE ??= "${DL_DIR}/${SDV_DL_FILENAME}"

CONTAINERS_TARGET_PATH ??= "/var/containers/images"

# Override this to specify a different location
SKOPEO_AUTH_JSON_FILE ??= "${TOPDIR}/auth.json"
SKOPEO_AUTH_PARAMETER = ""

FALSE = "0"
TRUE = "1"

# Documentation of configuration variables
CONTAINER_ARCH[doc] = "Specify the container machine architecture, e.g. amd64, arm64"
CONTAINER_OS[doc] = "Specify the container operatin system, e.g. linux"
CONTAINER_REGISTRY_REQUIRES_AUTH[doc] = "Specify if the container registry requires authentication: 1=true (default) and 0=false"
CONTAINER_SKIP_MISSING_ARCH[doc] = "Set to 1 to ignore errors due to missing container image architecture in remote container registry: 1=skip 0=fail build"
CONTAINERS_TARGET_PATH[doc] = "The location on the rootfs to store container archives"
SDV_DL_FILE[doc] = "Specify how the archive is downloaded"

do_fetch_container[depends] += "skopeo-native:do_populate_sysroot"
do_fetch_container[depends] += "jq-native:do_populate_sysroot"

# Refetch containers every time for now during development
do_compile[noexec] = "1"
do_fetch_container[nostamp] = "1"
do_unpack_container[nostamp] = "1"
do_fetch_container[network] = "1"

contains_tag() {
    retvalue="${TRUE}"
    TAGS_IN_TAR=$(tar -xOf ${SDV_DL_FILE} manifest.json | jq -r .[0].RepoTags[])
    if ! echo "${TAGS_IN_TAR}" | grep -q "${SDV_IMAGE_REF}:${SDV_IMAGE_TAG}" ;
    then
        bbwarn "Container image is missing expected tag: ${SDV_IMAGE_REF}:${SDV_IMAGE_TAG}"
        bbwarn "Container image contains the following tags: ${TAGS_IN_TAR}"
        CONTAINER_MANIFEST=$(tar -xOf ${SDV_DL_FILE} manifest.json)
        bbwarn "Container image manifest: ${CONTAINER_MANIFEST}"
        retvalue="${FALSE}"
    fi
    echo "${retvalue}"
}

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

    if [ ${CONTAINER_REGISTRY_REQUIRES_AUTH} -eq 1 ]
    then
        if [ -z "${SKOPEO_AUTH_JSON_FILE}" ]
        then
            bbfatal "Auth file for skopeo not set, please set SKOPEO_AUTH_JSON_FILE"
            exit 1
        else
            if [ ! -r "${SKOPEO_AUTH_JSON_FILE}" ]
            then
                bbfatal "Auth file as set by SKOPEO_AUTH_JSON_FILE not found: ${SKOPEO_AUTH_JSON_FILE}"
                exit 2
            fi
            SKOPEO_AUTH_PARAMETER="--authfile ${SKOPEO_AUTH_JSON_FILE}"
            if ! PATH=/usr/bin:${PATH} skopeo login ${SKOPEO_AUTH_PARAMETER} ${CONTAINER_REGISTRY} ;
            then
                bbwarn "Not logged into ${CONTAINER_REGISTRY}, download of container image ${SDV_IMAGE_REF} may fail!"
            fi
        fi 
    else
        SKOPEO_AUTH_PARAMETER=""
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

    if [ -f ${SDV_DL_FILE} ]; 
    then 
        if [ ! -s ${SDV_DL_FILE} ];
        then
            # Bugfix: Redownload if container has zero size (skopeo creates empty files on error)
            bbwarn "Deleting zero-size container image file: ${SDV_DL_FILE}. Will redownload."
            rm ${SDV_DL_FILE}
        elif [ $( contains_tag ) = "${FALSE}" ];
        then
            # Redownload when updating container version
            bbwarn "Deleting previously existing ${SDV_DL_FILE} with mismatching tag. Will redownload."
            rm ${SDV_DL_FILE}
        fi
    fi

    if [ ! -f ${SDV_DL_FILE} ];
    then
        if ! PATH=/usr/bin:${PATH} \
            skopeo \
            --debug \
            --override-arch ${CONTAINER_ARCH} \
            --override-os ${CONTAINER_OS} \
            copy \
            ${SKOPEO_AUTH_PARAMETER} \
            --additional-tag ${SDV_IMAGE_REF}:${SDV_IMAGE_TAG} \
            docker://${SDV_IMAGE_REF}:${SDV_IMAGE_TAG} \
            docker-archive:${SDV_DL_FILE}:${SDV_IMAGE_REF} ;
        then
            RC_SKOPEO=$?
            if [ ${CONTAINER_SKIP_MISSING_ARCH} -eq 0 ]
            then
                bbwarn "Error copying container image. rc=${RC_SKOPEO}"
            else
                bbwarn "Ignoring error copying container image due to CONTAINER_SKIP_MISSING_ARCH=1 (skopeo rc=${RC_SKOPEO})"
                exit 0
            fi
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

    contains_tag

    exit 0
}

do_unpack_container() {
    mkdir -p ${S}
    cp ${SDV_DL_FILE} ${S}/${SDV_DL_FILENAME}
}

do_install() {
    mkdir -p ${D}${CONTAINERS_TARGET_PATH}
    cp --no-dereference --preserve=mode,links -v ${S}/${SDV_DL_FILENAME} ${D}${CONTAINERS_TARGET_PATH}/
}

addtask do_fetch_container before do_unpack_container after do_fetch 
addtask do_unpack_container before do_install after do_fetch_container 

FILES:${PN} += "${CONTAINERS_TARGET_PATH}/${SDV_DL_FILENAME}"

PACKAGES = "${PN}"
