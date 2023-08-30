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

SRC_URI:append = " \
    file://ssh.service \
    file://mqtt.service \
"

do_install:append() {
    install -m 644 ${WORKDIR}/ssh.service ${D}${sysconfdir}/avahi/services/
    install -m 644 ${WORKDIR}/mqtt.service ${D}${sysconfdir}/avahi/services/
}
