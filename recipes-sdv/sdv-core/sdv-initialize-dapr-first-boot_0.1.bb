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

SUMMARY = "Initial boot script"
DESCRIPTION = "Script to do any first boot init, started as a systemd service which removes itself once finished"
LICENSE = "Apache-2.0"

inherit systemd features_check

SRC_URI =  " \
    file://LICENSE \
    file://first-boot-initservice/ \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "dapr-init.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "${sbindir}/dapr-init.sh \
                ${systemd_system_unitdir}/dapr-init.service"

REQUIRED_DISTRO_FEATURES = "systemd"

do_install:append () {
    install -d ${D}/${sbindir}
    install -m 0755 ${WORKDIR}/first-boot-initservice/dapr-init.sh ${D}/${sbindir}

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/first-boot-initservice/dapr-init.service ${D}${systemd_system_unitdir}
}
