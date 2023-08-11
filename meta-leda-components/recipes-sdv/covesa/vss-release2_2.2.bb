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

SUMMARY = "Covesa VSS Release ${PV}"
HOMEPAGE = "https://github.com/COVESA/vehicle_signal_specification"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://git/LICENSE;md5=9741c346eef56131163e13b9db1241b3"

SRC_URI = "git://github.com/COVESA/vehicle_signal_specification.git;protocol=https;nobranch=1;branch=release/2.2 \
           https://github.com/COVESA/vehicle_signal_specification/releases/download/v${PV}/vss_rel_${PV}.json;name=json \
"
SRC_URI[json.sha256sum] = "405d57cd7c01223c45da6e3b1f0a13276abd1b2c11bc7419cf4d348efdfed36e"

SRCREV = "${AUTOREV}"
S = "${WORKDIR}"

FILES:${PN} = "${datadir}/vss"

do_install:append() {
    install -d ${D}${datadir}/vss
    install -m 0644 ${S}/vss_rel_${PV}.* ${D}${datadir}/vss
}
