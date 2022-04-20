DESCRIPTION = "Eclipse Kanto - Container Management"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/${PN}-${PV}/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

SRC_URI = "git://github.com/eclipse-kanto/container-management;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-kanto/container-management"

INSANE_SKIP:${PN} += "ldflags textrel"

inherit goarch
inherit go

#S = "${WORKDIR}/git"
#GO_WORKDIR ?= "${GO_IMPORT}"
#do_compile[dirs] = "${S}"
#inherit goarch
#inherit pkgconfig
#RDEPENDS:${PN} += "github.com-sirupsen-logrus"

do_compile() {
  
	export GOARCH="${TARGET_GOARCH}"
	export GOROOT="${STAGING_LIBDIR_NATIVE}/${TARGET_SYS}/go"
	export GOPATH="${S}/src/${GO_IMPORT}:${S}/src/${GO_IMPORT}/vendor"
  export GOFLAGS="-mod=readonly -v"

  echo "Current PWD: ${PWD}"
  echo "Current S: ${S}"
  echo "Current B: ${B}"
  echo "Current WORKDIR: ${WORKDIR}"
  echo "Current GOROOT: ${GOROOT}"
  echo "Current GOPATH: ${GOPATH}"
  echo "Current GO_IMPORT: ${GO_IMPORT}"
  echo "Current GOBIN: ${GOBIN}"
  echo "Current GOOS: ${GOOS}"
  echo "Current GOARCH: ${GOARCH}"
  echo "Current GOFLAGS: ${GOFLAGS}"

	export CGO_ENABLED="1"
	export CFLAGS=""
	export LDFLAGS=""
	export CGO_CFLAGS="${BUILDSDK_CFLAGS} --sysroot=${STAGING_DIR_TARGET}"
	export CGO_LDFLAGS="${BUILDSDK_LDFLAGS} --sysroot=${STAGING_DIR_TARGET}"
	export GO111MODULE=off

  cd ${S}/src/${GO_IMPORT}

  #cd ${S}/src
  
  env GOOS=$GOOS GOARCH=$GOARCH ${GO} build $GOFLAGS ./things/...
  env GOOS=$GOOS GOARCH=$GOARCH ${GO} build $GOFLAGS ./rollouts/...
  env GOOS=$GOOS GOARCH=$GOARCH ${GO} build $GOFLAGS ./containerm/...

}

do_install() {
  #install -d "${D}/${bindir}"
  #install -m 0755 "${S}/src/${GO_IMPORT}/edgecontainerd" "${D}/${bindir}"
}

