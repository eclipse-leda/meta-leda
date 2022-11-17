# ********************************************************************************
# * Copyright (c) 2022 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************

SUMMARY = "Render rich text, tables, progress bars, syntax highlighting, markdown and more to the terminal"
HOMEPAGE = "https://github.com/willmcgugan/rich"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b5f0b94fbc94f5ad9ae4efcf8a778303"

SRC_URI[sha256sum] = "ba3a3775974105c221d31141f2c116f4fd65c5ceb0698657a11e9f295ec93fd0"

inherit pypi setuptools3

RDEPENDS:${PN} += " python3-core python3-commonmark python3-pygments"

