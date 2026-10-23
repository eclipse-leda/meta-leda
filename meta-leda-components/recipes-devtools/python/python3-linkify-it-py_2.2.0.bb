SUMMARY = "Link recognition library with Unicode support"
HOMEPAGE = "https://github.com/tsutsu3/linkify-it-py"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a7aa82f01e7197249f7f2dcb0c0bac97"

PYPI_PACKAGE = "linkify_it_py"
SRC_URI[sha256sum] = "907acd2d17ac1fbb9ddb62c8957ccbd6158cac602231a15c3b0cd1e215f03cee"

inherit pypi python_setuptools_build_meta

RDEPENDS:${PN} += "python3-uc-micro-py"
