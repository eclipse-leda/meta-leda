SUMMARY = "Embed SDV container archives into the system"
DESCRIPTION = "Pull the container images, save them in the rootfs."

SRC_URI += "file://README.txt \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

RDEPENDS_${PN} = "skopeo bash"

do_compile[noexec] = "1"

do_fetch() {
    if [ ! -f "${DL_DIR}/sdv-databroker.oci" ];
    then
        if ! PATH=/usr/bin:${PATH} skopeo --override-arch ${TARGET_ARCH} copy docker://ghcr.io/softwaredefinedvehicle/swdc-os-vehicleapi/databroker:v0.6.0 oci:${DL_DIR}/sdv-databroker.oci; then
            bbfatal "Error copying container image"
        fi
    fi
}

do_install() {
    install -d ${D}/var/lib/sdv
    cp -R --no-dereference --preserve=mode,links -v ${DL_DIR}/sdv-databroker.oci ${D}/var/lib/sdv
}

FILES_${PN} += "${datadir}/${PN}/README.txt \
                ${datadir}/${PN}/sdv-databroker.oci \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"