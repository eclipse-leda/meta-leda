SUMMARY = "SDV Example Seat Service"
DESCRIPTION = "Example seat service"

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
        if ! PATH=/usr/bin:${PATH} skopeo copy docker://ghcr.io/softwaredefinedvehicle/swdc-os-vehicleapi/seat_service:v0.6.0 oci:${DL_DIR}/sdv-seat_service.oci; then
            bbfatal "Error copying container image. Proxy is ${http_proxy}"
        fi
    fi
}

do_install() {
    install -d ${D}/var/lib/sdv
    cp -R --no-dereference --preserve=mode,links -v ${DL_DIR}/sdv-seat_service.oci ${D}/var/lib/sdv
}

FILES_${PN} += "${datadir}/${PN}/README.txt \
                ${datadir}/${PN}/sdv-seat_service.oci \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"