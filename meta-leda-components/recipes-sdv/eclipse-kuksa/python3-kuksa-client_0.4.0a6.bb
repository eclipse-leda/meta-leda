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


# TODO: update to newer release with fixed license file and without bash dependency ...


LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=899c824b3a6926844787ad42a3e5df56"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PYPI_PACKAGE = "kuksa_client"
SRC_URI[sha256sum] = "148aa5eea5e7b760b859754770f456159062d9c405b206c66bc668b11166331c"

DEPENDS += " \
    python3-grpcio-tools-native \
"

RDEPENDS:${PN} = " \
    python3-cmd2 \
    python3-grpcio-tools \
    python3-websockets \
    bash \
"

inherit pypi setuptools3
