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

SRC_URI = "git://github.com/COVESA/vehicle_signal_specification.git;protocol=https;nobranch=1;branch=release/4.0 \
           https://github.com/COVESA/vehicle_signal_specification/releases/download/v${PV}/vss_rel_${PV}.json;name=json \
           https://github.com/COVESA/vehicle_signal_specification/releases/download/v${PV}/vss_rel_${PV}.yaml;name=yaml \
"
SRC_URI[json.sha256sum] = "925d9e1b5bd187694b3e03051a50777fdd5b46a5a5c4f48fd49ca270a07cdc50"
SRC_URI[yaml.sha256sum] = "49193cc5f70670037029470bf6e4b91ee7e74e6d4b042b851906b079121d8a59"

SRCREV = "${AUTOREV}"
S = "${WORKDIR}"

PACKAGES += " vss-spec4"

FILES:${PN} = "${datadir}/vss/*.*"
FILES:vss-spec4 = "${datadir}/vss/spec"

do_install:append() {
    install -d ${D}${datadir}/vss
    install -m 0644 ${S}/vss_rel_${PV}.* ${D}${datadir}/vss

    install -d ${D}${datadir}/vss/spec/${PV}
    cp -R --no-preserve=ownership ${S}/git/spec/* ${D}${datadir}/vss/spec/${PV}
}
