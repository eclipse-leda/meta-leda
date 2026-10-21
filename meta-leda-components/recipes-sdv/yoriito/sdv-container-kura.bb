SUMMARY = "Locally built Kura container archive for offline deployment"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

DEPENDS = "skopeo-native"
S = "${UNPACKDIR}"
CONTAINERS_TARGET_PATH = "/var/containers/images"

do_compile[noexec] = "1"
do_install[depends] += "sdv-image-kura:do_image_complete"

do_install() {
    install -d ${D}${CONTAINERS_TARGET_PATH}
    skopeo --insecure-policy copy \
        oci:${DEPLOY_DIR_IMAGE}/sdv-image-kura-integration-oci:integration \
        docker-archive:${B}/kura.tar:${LEDA_YORIITO_CONTAINER_REF}
    digest=$(sha256sum ${B}/kura.tar | cut -d ' ' -f 1)
    install -m 0644 ${B}/kura.tar ${D}${CONTAINERS_TARGET_PATH}/kura-${digest}.tar
}

FILES:${PN} = "${CONTAINERS_TARGET_PATH}/kura-*.tar"
PACKAGE_ARCH = "${MACHINE_ARCH}"
