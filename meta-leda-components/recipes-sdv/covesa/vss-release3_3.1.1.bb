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

SRC_URI = "git://github.com/COVESA/vehicle_signal_specification.git;protocol=https;nobranch=1;branch=release/3.1 \
           https://github.com/COVESA/vehicle_signal_specification/releases/download/v${PV}/vss_rel_${PV}.json;name=json \
           https://github.com/COVESA/vehicle_signal_specification/releases/download/v${PV}/vss_rel_${PV}.yaml;name=yaml \
"
SRC_URI[json.sha256sum] = "eaf21f23ec1461660a55202ea64a49eba1a9dc082f188b22788d6b526e9a09f5"
SRC_URI[yaml.sha256sum] = "3d57fde11174f43974f719ef27d3c4d6a9ec1a25fe2f9a878f53411fd4d43763"

SRCREV = "${AUTOREV}"
S = "${WORKDIR}"

PACKAGES += " vss-spec3"

FILES:${PN} = "${datadir}/vss/*.*"
FILES:vss-spec3 = "${datadir}/vss/spec"

do_install:append() {
    install -d ${D}${datadir}/vss
    install -m 0644 ${S}/vss_rel_${PV}.* ${D}${datadir}/vss

    install -d ${D}${datadir}/vss/spec/${PV}
    cp -R --no-preserve=ownership ${S}/git/spec/* ${D}${datadir}/vss/spec/${PV}
}
