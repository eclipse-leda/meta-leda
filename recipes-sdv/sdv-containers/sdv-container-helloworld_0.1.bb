SUMMARY = "Embed SDV container archives into the system"
DESCRIPTION = "Pull the container images, save them in the rootfs."

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.docker.io_hello-world;md5=xxxx"

SRC_URI += "file://README.txt \
            file://images.manifest \
            file://LICENSE.docker.io_hello-world"

STORE_DIR = "${WORKDIR}/container-store"

RDEPENDS_${PN} = "skopeo bash"

inherit allarch
do_compile[noexec] = "1"

do_fetch() {
    mkdir -p "${STORE_DIR}"
    #DL_DIR = "./build/downloads"
    if [ ! -f "${DL_DIR}/hello-world.oci" ];
    then
        if ! PATH=/usr/bin:${PATH} skopeo copy docker://hello-world:latest docker-archive:${DL_DIR}/hello-world.oci; then
            bbfatal "Error copying container image"
        fi
    fi
}

do_unpack() {
    cp ${DL_DIR}/hello-world.oci ${WORKDIR}
}

do_install() {
    install -d "${D}${datadir}/${PN}"
    install -m 0400 "${WORKDIR}/README.txt" "${D}${datadir}/${PN}"
    install -m 0400 "${WORKDIR}/images.manifest" "${D}${datadir}/${PN}"
    install -m 0400 "${WORKDIR}/hello-world.oci" "${D}${datadir}/${PN}"
    # rm ${STORE_DIR}/hello-world.oci
}

#FILES = "${datadir}/container-images/README.txt \
#${datadir}/container-images/images.manifest \
#${datadir}/container-images/hello-world.oci"

FILES_${PN} += "${datadir}/container-images/README.txt \
                ${datadir}/container-images/images.manifest \
                ${datadir}/container-images/hello-world.oci"

PACKAGES = "${PN}"