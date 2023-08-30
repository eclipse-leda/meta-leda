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

SUMMARY = "COVESA Vehicle Signal Specification tooling."
HOMEPAGE = "https://github.com/COVESA/vss-tools"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9741c346eef56131163e13b9db1241b3"

SRC_URI = "https://files.pythonhosted.org/packages/4b/4c/5fb9aa2e891050b383935b9a2379a68251974f5ec4215dbbb4d854f48ad4/vss-tools-${PV}.tar.gz"
SRC_URI[md5sum] = "9d6a91e47e54f00f778443faf477269a"
SRC_URI[sha1sum] = "cbfc9d5c6c21f903d8445c4cb4ad22ec5e8e069c"
SRC_URI[sha256sum] = "abfb9e95ea67af842d4a9931f0077c41e65955515f902237b67843377838f868"
SRC_URI[sha384sum] = "6a74379044f241c19b423b0b9087aa78196c3b61d2149ea50bb065f30220f8e3c1d6fb7834e7b1e755b6ceee840d28a3"
SRC_URI[sha512sum] = "9640fa88af856eef1f447e2e7ed3df4cc450cb7d572a4c244757112d813243b47a461d6981f6bf0ac458f29092dcf66f0588b6dd088cc47368e559719713ee76"

PYPI_PACKAGE = "vss-tools"

inherit pypi setuptools3

RDEPENDS:${PN} += "python3-anytree python3-deprecation python3-graphql-core python3-pyyaml"
