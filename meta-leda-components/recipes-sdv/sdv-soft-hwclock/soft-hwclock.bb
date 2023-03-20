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

DESCRIPTION = "Fake Hardware Clock"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://soft-hwclock"
SRC_URI:append = " file://soft-hwclock.service"
SRC_URI:append = " file://soft-hwclock-tick.service"
SRC_URI:append = " file://soft-hwclock-tick.timer"

# SRC_URI = "https://github.com/kristjanvalur/soft-hwclock"
# SRC_URI[sha256sum] = "1268c4fee70172603845c4d305fbfc1b06f04a228d16515e7001d4f836b331e6"

inherit systemd features_check

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "soft-hwclock.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

REQUIRED_DISTRO_FEATURES = "systemd"

PACKAGES = "${PN}"
FILES:${PN} += "/opt/soft-hwclock/soft-hwclock"
FILES:${PN} += "/opt/soft-hwclock/soft-hwclock.data"
FILES:${PN} += "${systemd_unitdir}/system/soft-hwclock.service"
FILES:${PN} += "${systemd_unitdir}/system/soft-hwclock-tick.service"
FILES:${PN} += "${systemd_unitdir}/system/soft-hwclock-tick.timer"

do_install:append() {

    install -d ${D}/opt/soft-hwclock
    install -m 0755 ${WORKDIR}/soft-hwclock ${D}/opt/soft-hwclock

    CTIME=$(date +%s)
    echo "${CTIME}" > ${D}/opt/soft-hwclock/soft-hwclock.data

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/soft-hwclock.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/soft-hwclock-tick.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/soft-hwclock-tick.timer ${D}${systemd_unitdir}/system

}

SRC_URI += "file://LICENSE"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=a109bd21357ed8a1fa56e8879764d28d"
