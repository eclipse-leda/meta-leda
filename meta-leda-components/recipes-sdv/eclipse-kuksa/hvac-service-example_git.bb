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

SUMMARY = "Eclipse Kuksa - HVAC Service Example"
DESCRIPTION = "The HVAC service is a service dummy allowing to control the state of the A/C and the desired cabin temperature."
HOMEPAGE = "https://github.com/eclipse/kuksa.val.services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

SRC_URI = "git://github.com/eclipse/kuksa.val.services;protocol=https;branch=main"
SRCREV = "3a4fff12f2c9a6f96064e5382e4e3b03ea32090b"
PV = "0.2.0+git${SRCPV}"

S = "${WORKDIR}/git/hvac_service"

inherit setuptools3

RDEPENDS:${PN} += "python3-asyncio python3-core python3-grpcio python3-logging python3-protobuf"

do_install:append() {
    install -d "${D}/${bindir}"
    install -m 755 "${S}/hvacservice.py" "${D}/${bindir}/hvacservice"
}

FILES:${PN} += " \
    ${bindir}/hvacservice \
"
