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

SUMMARY = "KantoCM Zeroconf"
DESCRIPTION = "System service to publish services on running containers dynamically with Zeroconf"

SRC_URI = "git://github.com/eclipse-leda/leda-utils;protocol=https;branch=main \
           git://github.com/eclipse-kanto/container-management;name=kanto;protocol=https;branch=main;subdir=git/kanto_cm/api;subpath=containerm/api \
           "
SRCREV = "d27289484a36a618b5c33b81dda0e944b60f8bdf"
SRCREV_kanto = "e7fb861984deba4b7e7ba526c04ff695b1367cdc"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PACKAGES = "${PN}"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit python3native python3-dir
inherit systemd

DEPENDS += "python3-grpcio-tools-native"
RDEPENDS:${PN} += "container-management avahi-daemon python3-grpcio python3-zeroconf"

RDEPENDS:${PN} += "avahi-utils"

SYSTEMD_SERVICE:${PN} = "kantocm-zeroconf.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_compile:append() {
    python3 -m grpc.tools.protoc -I${S}/kanto_cm --python_out=${B} --grpc_python_out=${B} \
        ${S}/kanto_cm/api/services/containers/*.proto ${S}/kanto_cm/api/types/containers/*.proto
    find ${B} -name "*.py" | xargs sed -i -e "s/from api/from kanto_cm.api/g"
}

do_install() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}/kanto_cm
    cp -R --no-preserve=ownership ${B}/* ${D}${PYTHON_SITEPACKAGES_DIR}/kanto_cm

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/src/python/kantocm-zeroconf/kantocm-zeroconf.service ${D}${systemd_system_unitdir}

    install -d ${D}${bindir}
    install -m 0755 ${S}/src/python/kantocm-zeroconf/kantocm_zeroconf.py ${D}${bindir}/kantocm_zeroconf
}

FILES:${PN} += "${PYTHON_SITEPACKAGES_DIR}/kanto_cm"
