DESCRIPTION = "Eclipse Kanto - Container Management"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

SRC_URI = "git://github.com/eclipse-kanto/container-management;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-kanto/container-management"

S = "${WORKDIR}/git"

PREFERRED_VERSION_go ?= "1.18"

inherit go-mod

do_compile() {
  cd ${B}/src/${GO_IMPORT}
  ${GO} build ${GOBUILDFLAGS} ./things/...
  ${GO} build ${GOBUILDFLAGS} ./rollouts/...
  ${GO} build ${GOBUILDFLAGS} -o ${B}/edgecontainerd ./containerm/daemon
  ${GO} build ${GOBUILDFLAGS} -o ${B}/edgectr ./containerm/cli
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/edgecontainerd" "${D}/${bindir}"
  install -m 0755 "${B}/edgectr" "${D}/${bindir}"
}

#@env GOOS=${GOOS} GOARCH=${GOARCH} go build ${GOFLAGS} -ldflags ${DEFAULT_LDFLAGS} ${GOBUILD_TAGS} -o ${INSTALL_ROOT}/${GOOS}_${GOARCH}/${DAEMON_BINARY_NAME} bosch.io/edge/containerm/daemon || exit 1
#@env GOOS=${GOOS} GOARCH=${GOARCH} go build ${GOFLAGS} -ldflags ${DEFAULT_LDFLAGS} ${GOBUILD_TAGS} -o ${INSTALL_ROOT}/${GOOS}_${GOARCH}/${CLI_BINARY_NAME} bosch.io/edge/containerm/cli || exit 1



