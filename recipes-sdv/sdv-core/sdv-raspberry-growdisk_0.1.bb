# /********************************************************************************
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
# ********************************************************************************/

SUMMARY = "Grow disk space on Raspberry Pi"
DESCRIPTION = "Script to enlarge disk partition to size of SD Card"
LICENSE = "Apache-2.0"

inherit systemd features_check

SRC_URI =  " \
    file://LICENSE \
    file://raspberry-growdisk/ \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "raspberry-growdisk.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "${sbindir}/raspberry-growdisk.sh \
                ${systemd_system_unitdir}/raspberry-growdisk.service"

REQUIRED_DISTRO_FEATURES = "systemd"

do_install:append () {
    install -d ${D}/${sbindir}
    install -m 0755 ${WORKDIR}/raspberry-growdisk/raspberry-growdisk.sh ${D}/${sbindir}

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/raspberry-growdisk/raspberry-growdisk.service ${D}${systemd_system_unitdir}
}
