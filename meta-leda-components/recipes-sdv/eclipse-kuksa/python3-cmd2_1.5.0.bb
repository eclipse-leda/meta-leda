# This old version is needed for python3-kuksa-client.
# Recipe copied from hardknott release:
# https://git.openembedded.org/meta-openembedded/tree/meta-python/recipes-devtools/python/python3-cmd2_1.5.0.bb?h=hardknott

SUMMARY = "Extra features for standard library's cmd module"
HOMEPAGE = "https://github.com/python-cmd2/cmd2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9791cd24ca7d1807388ccd55cd066def"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[sha256sum] = "701a8c9975c4abc45e5d13906ab149f959f812869106347323a3f89ac0e82a62"

inherit pypi setuptools3

RDEPENDS:${PN} += "\
    ${PYTHON_PN}-attrs \
    ${PYTHON_PN}-colorama \
    ${PYTHON_PN}-pyperclip \
    ${PYTHON_PN}-wcwidth \
"

BBCLASSEXTEND = "native nativesdk"
