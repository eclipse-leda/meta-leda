# ********************************************************************************
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
# ********************************************************************************

SUMMARY = "KUKSA.val Python Client and SDK"
HOMEPAGE = "https://github.com/eclipse/kuksa.val"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

PYPI_PACKAGE = "kuksa_client"
SRC_URI[sha256sum] = "548efd32ab5c4f0004f922b36eba88f1dc8c374bf3c599f1337b6b74e0fcf448"

DEPENDS += " \
    python3-grpcio-tools-native \
"

RDEPENDS:${PN} = " \
    python3-cmd2 \
    python3-grpcio-tools \
    python3-websockets \
"

inherit pypi setuptools3
