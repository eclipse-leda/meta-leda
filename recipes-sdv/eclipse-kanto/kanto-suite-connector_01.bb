DESCRIPTION = "Eclipse Kanto - Bosch IoT Suite Connector"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

SRC_URI = "git://github.com/eclipse-kanto/suite-connector;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-kanto/suite-connector"

S = "${WORKDIR}/git"

inherit go-mod

do_compile() {
    cd ${B}/src/${GO_IMPORT}
    ${GO} build ${GOBUILDFLAGS} -o ${B}/ ./cmd/connector/
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/connector" "${D}/${bindir}"
}


