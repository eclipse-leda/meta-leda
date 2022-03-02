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
    if [ ! -f "${DL_DIR}/hello-world.oci" ];
    then
        if ! PATH=/usr/bin:${PATH} skopeo copy docker://hello-world:latest oci:${DL_DIR}/hello-world.oci; then
            bbfatal "Error copying container image"
        fi
    fi
}

do_install() {
    install -d ${D}/var/lib/sdv
    cp -R --no-dereference --preserve=mode,links -v ${DL_DIR}/hello-world.oci ${D}/var/lib/sdv
}

FILES_${PN} += "${datadir}/${PN}/README.txt \
                ${datadir}/${PN}/hello-world.oci \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"