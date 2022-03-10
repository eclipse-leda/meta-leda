# /********************************************************************************
# * Copyright (c) 2021 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0
# *
# * SPDX-License-Identifier: EPL-2.0
# ********************************************************************************/

# Define defaults, which can be overridden within the recipe
export CONTAINER_ARCH

CONTAINER_OS ??= "linux"
SDV_DL_FILE ??= "${DL_DIR}/${PN}-${PV}.oci"

# Documentation of configuration variables
CONTAINER_ARCH[doc] = "Specify the container machine architecture, e.g. amd64, arm64"
CONTAINER_OS[doc] = "Specify the container operatin system, e.g. linux"
SDV_DL_FILE[doc] = "Specify how the archive is downloaded"

#DEPENDS = "skopeo"
#RDEPENDS:${PN} = "containerd"

do_compile[noexec] = "1"

# TODO: Refetch containers every time for now during development
# do_fetch_container[nostamp] = "1"
# do_unpack_container[nostamp] = "1"

do_fetch_container() {   
    SKOPEO_LOC=$(PATH=/usr/bin:${PATH} whereis skopeo)
    bbnote "Skopeo Location: ${SKOPEO_LOC}"

    if ! PATH=/usr/bin:${PATH} skopeo login --authfile ~/auth.json ghcr.io
    then
        bbwarn "Not logged into ghcr.io, download of container image may fail"
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

    if [ ! -f ${SDV_DL_FILE} ];
    then
        if ! PATH=/usr/bin:${PATH} skopeo --override-arch ${CONTAINER_ARCH} --override-os ${CONTAINER_OS} copy --authfile ~/auth.json ${SDV_IMAGE_REF} docker-archive:${SDV_DL_FILE};
        then
            bbfatal "Error copying container image. Proxy is ${http_proxy}"
        fi
    fi
}

do_unpack_container() {
    CONTAINER_SOURCE_FOLDER="${S}/container-image"
    bbnote "Copying ${SDV_DL_FILE} to ${CONTAINER_SOURCE_FOLDER}"
    cp ${SDV_DL_FILE} ${CONTAINER_SOURCE_FOLDER}
}

# Todo: Move the layer blobs into the containerd storage
do_install() {
    install -d ${D}/var/lib/sdv
    cp -R --no-dereference --preserve=mode,links -v ${S}/container-image ${D}/var/lib/sdv/${PN}
}

addtask do_fetch_container before do_unpack_container after do_fetch 
addtask do_unpack_container before do_install after do_fetch_container 
