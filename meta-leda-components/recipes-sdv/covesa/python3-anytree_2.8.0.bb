# /********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************/

SUMMARY = "Powerful and Lightweight Python Tree Data Structure with various plugins"
HOMEPAGE = "https://github.com/c0fec0de/anytree"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI[sha256sum] = "3f0f93f355a91bc3e6245319bf4c1d50e3416cc7a35cc1133c1ff38306bbccab"

PYPI_PACKAGE = "anytree"

inherit pypi setuptools3

RDEPENDS:${PN} = " \
    ${PYTHON_PN}-six \
"

do_install:append() {
    rm ${D}${prefix}/LICENSE
}

BBCLASSEXTEND = "native"
