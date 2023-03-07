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

DESCRIPTION = "wpa_supplicant start up"

PROVIDES:${PN} += "wpa"
RPROVIDES:${PN} += "wpa"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://wpa.service"

SERVICE_DIR = "${systemd_unitdir}/system"

install_service() {
    install -d ${D}${SERVICE_DIR}
    install -m 0644 ${WORKDIR}/wpa.service ${D}${SERVICE_DIR}
}

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${SYSCONFDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
PACKAGES = "${PN}"
FILES:${PN} += "${SERVICE_DIR}/wpa.service"
