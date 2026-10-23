SUMMARY = "Autocomplete dropdowns for Textual apps"
HOMEPAGE = "https://github.com/darrenburns/textual-autocomplete"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5112a23b1c70fd6378456f90626ee0fe"

PYPI_PACKAGE = "textual_autocomplete"
SRC_URI[sha256sum] = "2ba2f0d767be4480ecacb3e4b130cf07340e033c3500fc424fed9125d27a4586"

inherit pypi python_hatchling

RDEPENDS:${PN} += " \
    python3-textual \
    python3-typing-extensions \
"
