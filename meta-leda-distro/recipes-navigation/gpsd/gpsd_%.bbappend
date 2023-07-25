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

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://gpsd_default_config"

do_install:append() {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/gpsd_default_config ${D}${sysconfdir}/default/gpsd.default
}

SYSTEMD_SERVICE:${PN} = "${BPN}.service ${BPN}ctl@.service"
