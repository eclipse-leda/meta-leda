DESCRIPTION = "Eclipse Kanto - Software Update"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

SRC_URI = "git://github.com/eclipse-kanto/software-update;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-kanto/software-update"

S = "${WORKDIR}/git"

inherit go
inherit go-mod

do_compile() {
    export GOARCH="${TARGET_GOARCH}"

    #export GOPATH="${S}/src/${GO_IMPORT}:${STAGING_DIR_TARGET}/${prefix}/local/go"
    #export GOROOT="${STAGING_DIR_NATIVE}/${nonarch_libdir}/${HOST_SYS}/go"

    # Pass the needed cflags/ldflags so that cgo
    # can find the needed headers files and libraries
    #export CGO_ENABLED="1"
    #export CGO_CFLAGS="${CFLAGS} --sysroot=${STAGING_DIR_TARGET}"
    #export CGO_LDFLAGS="${LDFLAGS} --sysroot=${STAGING_DIR_TARGET}"
    #export BUILDTAGS="no_btrfs static_build netgo"
    #export CFLAGS="${CFLAGS}"
    #export LDFLAGS="${LDFLAGS}"
    #export SHIM_CGO_ENABLED="${CGO_ENABLED}"
    # fixes:
    # cannot find package runtime/cgo (using -importcfg)
    #        ... recipe-sysroot-native/usr/lib/aarch64-poky-linux/go/pkg/tool/linux_amd64/link:
    #        cannot open file : open : no such file or directory
    #export GO_BUILD_FLAGS="-a -pkgdir dontusecurrentpkgs"
    #export GO111MODULE=off

    echo "Current S: ${S}"
    echo "Current B: ${B}"
    echo "Current GOPATH: ${GOPATH}"
    echo "Current GOOS: ${GOOS}"
    echo "Current GOARCH: ${GOARCH}"
    echo "Current WORKDIR: ${WORKDIR}"
    echo "Current GOROOT: ${GOROOT}"
    echo "Current GOPATH: ${GOPATH}"
    echo "Current GO_IMPORT: ${GO_IMPORT}"
    echo "Current GOBIN: ${GOBIN}"
    echo "Current GOFLAGS: ${GOFLAGS}"
    echo "Current GO: ${GO}"
    echo "Current GOBUILDFLAGS: ${GOBUILDFLAGS}"
    cd ${B}/src/${GO_IMPORT}
    ${GO} build ${GOBUILDFLAGS} -o ${B}/ ./cmd/software-update/
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/software-update" "${D}/${bindir}"
}


