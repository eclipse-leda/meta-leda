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

DESCRIPTION = "DHCP Client configuration for WiFi with iwd"

PROVIDES:${PN} += "iwd-config"
RPROVIDES:${PN} += "iwd-config"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = " file://main.conf"

do_install() {
    install -d ${D}/etc/iwd
    install -m 0644 ${WORKDIR}/main.conf ${D}/etc/iwd
}

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PACKAGES = "${PN}"
FILES:${PN} += "/etc/iwd/main.conf"

