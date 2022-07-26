DESCRIPTION = "k9s cli"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

SRC_URI = "git://github.com/derailed/k9s;protocol=https;branch=master"
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/derailed/k9s"

S = "${WORKDIR}/git"

PREFERRED_VERSION_go ?= "1.18"

inherit go-mod

do_compile() {
  cd ${B}/src/${GO_IMPORT}
  ${GO} build ${GOBUILDFLAGS} -o ${B}/k9s .
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/k9s" "${D}/${bindir}"
}


