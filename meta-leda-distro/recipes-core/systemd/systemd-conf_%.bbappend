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
#
# Enable CAN bus network by default
#

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " file://can0.network"

FILES:${PN} += " ${sysconfdir}/systemd/network/can0.network"

do_install:append() {
    install -D -m 0644 ${WORKDIR}/can0.network ${D}${systemd_unitdir}/network
}
