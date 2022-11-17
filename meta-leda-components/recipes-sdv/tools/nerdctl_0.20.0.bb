DESCRIPTION = "containerd - nerdctl"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/containerd/nerdctl;protocol=https;branch=main"
SRCREV = "e77e05b5fd252274e3727e0439e9a2d45622ccb9"
PV = "0.20.0+git${SRCPV}"

GO_IMPORT = "github.com/containerd/nerdctl"

S = "${WORKDIR}/git"

PREFERRED_VERSION_go ?= "1.18"

inherit go-mod

do_compile() {
  cd ${B}/src/${GO_IMPORT}
  ${GO} build ${GOBUILDFLAGS} -o ${B}/nerdctl ./cmd/nerdctl
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/nerdctl" "${D}/${bindir}"
}


