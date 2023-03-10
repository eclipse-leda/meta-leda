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

DESCRIPTION = "Start up wpa_supplicant"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://wpa.service"
SRC_URI:append = " file://wpa.sh"

inherit systemd features_check

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "wpa.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

REQUIRED_DISTRO_FEATURES = "systemd"

do_install:append() {
    install -d ${D}/${sbindir}
    install -m 0755 ${WORKDIR}/wpa.sh ${D}/${sbindir}

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/wpa.service ${D}${systemd_system_unitdir}
}

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
PACKAGES = "${PN}"
FILES:${PN} += "${sbindir}/wpa.sh"
FILES:${PN} += "${systemd_system_unitdir}/wpa.service"
