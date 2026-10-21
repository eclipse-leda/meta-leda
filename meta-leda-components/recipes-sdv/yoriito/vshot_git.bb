SUMMARY = "Yoriito VISS command-line and terminal UI client"
DESCRIPTION = "A CLI and TUI client for interacting with the Kura VISS server."
HOMEPAGE = "https://github.com/yoriito/kura"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../../LICENSE;md5=e23fadd6ceef8c618fc1c65191d846fa"

SRC_URI = "gitsm://github.com/yoriito/kura.git;protocol=https;branch=main \
           file://0001-support-python-3.14.patch \
           file://bool_utils.py \
           file://vshot"
SRCREV = "3489f0ba019b59eb6160090000be2644f1d1ef2d"

S = "${UNPACKDIR}/${BP}/client/vshot"

inherit python3-dir

do_compile[noexec] = "1"

RDEPENDS:${PN} = " \
    python3-core \
    python3-click \
    python3-click-repl \
    python3-grpcio \
    python3-protobuf \
    python3-rich \
    python3-termcolor \
    python3-textual \
    python3-textual-autocomplete \
"

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
    cp -R ${S}/vshot ${D}${PYTHON_SITEPACKAGES_DIR}/
    install -m 0644 ${UNPACKDIR}/bool_utils.py ${D}${PYTHON_SITEPACKAGES_DIR}/vshot/

    install -d ${D}${bindir}
    install -m 0755 ${UNPACKDIR}/vshot ${D}${bindir}/vshot
}

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/vshot"
