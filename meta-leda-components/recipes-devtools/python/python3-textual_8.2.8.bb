SUMMARY = "Modern Text User Interface framework"
HOMEPAGE = "https://github.com/Textualize/textual"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=efa34cbda5817e1e7c540c6cff8f033d"

SRC_URI[sha256sum] = "3f106a9fbc73e39dd266c9712432087de78a6d644084c7c241d6a25c3169115b"

inherit pypi python_poetry_core

RDEPENDS:${PN} += " \
    python3-linkify-it-py \
    python3-markdown-it-py \
    python3-mdit-py-plugins \
    python3-platformdirs \
    python3-pygments \
    python3-rich \
    python3-typing-extensions \
"
