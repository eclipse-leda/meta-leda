SUMMARY = "Kanto deployment descriptor for the Kura runtime"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit kanto-auto-deployer

SRC_URI = "file://kura.json"
S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    sed 's|@KURA_IMAGE@|${LEDA_YORIITO_CONTAINER_REF}|g' \
        ${UNPACKDIR}/kura.json > ${D}${KANTO_MANIFESTS_LOCAL_DIR}/kura.json
}

FILES:${PN} = "${KANTO_MANIFESTS_LOCAL_DIR}/kura.json"
