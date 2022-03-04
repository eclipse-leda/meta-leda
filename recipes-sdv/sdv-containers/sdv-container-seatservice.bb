SUMMARY = "SDV Example Seat Service"
DESCRIPTION = "Example seat service"

SRC_URI += "file://README.txt \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

RDEPENDS_${PN} = "skopeo"

do_compile[noexec] = "1"

# Map TARGET_ARCH to CONTAINER_ARCH

# BitBake Target Arch: x86_64
# Container Arch: linux/amd64

CONTAINER_ARCH="amd64"
CONTAINER_OS="linux"

SDV_IMAGE_REF="docker://ghcr.io/softwaredefinedvehicle/swdc-os-vehicleapi/seat_service:v0.6.0"
SDV_DL_FILE="${DL_DIR}/sdv-databroker.oci"

do_fetch() {
    SKOPEO_LOC=$(PATH=/usr/bin:${PATH} whereis skopeo)
    bbnote "Skopeo Location: ${SKOPEO_LOC}"

    if ! PATH=/usr/bin:${PATH} skopeo login --get-login ghcr.io;
    then
        bbwarn "Not logged into ghcr.io, download of container image may fail"
    fi


    bbplain "Fetching container image: ${SDV_IMAGE_REF}"
    bbplain "Target container architecture: ${CONTAINER_ARCH}"
    bbplain "Target container operating system: ${CONTAINER_OS}"
    bbplain "Storing to: ${SDV_DL_FILE}"

    if [ ! -f ${SDV_DL_FILE} ];
    then
        # TODO: Use "skopeo --override-arch ${CONTAINER_ARCH}" to override for the target architecture
        if ! PATH=/usr/bin:${PATH} skopeo --override-arch ${CONTAINER_ARCH} --override-os ${CONTAINER_OS} copy ${SDV_IMAGE_REF} oci-archive:${SDV_DL_FILE};
        then
            bbfatal "Error copying container image. Proxy is ${http_proxy}"
        fi
    fi
}

do_unpack() {
    CONTAINER_SOURCE_FOLDER="${S}/container-image"
    bbplain "Unpacking ${SDV_DL_FILE} to ${CONTAINER_SOURCE_FOLDER}"
    if ! PATH=/usr/bin:${PATH} skopeo copy oci-archive:${SDV_DL_FILE} oci:${CONTAINER_SOURCE_FOLDER};
    then
        bbfatal "Error unpacking container image to ${CONTAINER_SOURCE_FOLDER}"
    fi
}

do_install() {
    install -d ${D}/var/lib/sdv/container-image
    cp -R --no-dereference --preserve=mode,links -v ${S}/container-image ${D}/var/lib/sdv/container-image
}

FILES_${PN} += "${datadir}/${PN}/README.txt \
                ${datadir}/${PN}/container-image \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"