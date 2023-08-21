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
#
# Modifies the original Kanto-CM recipe to use a custom service file that adds a 
# After=data.mount dependency for CM. This way it is ensured that CM is started AFTER
# the data partition is properly mounted and the data partition is unmounted AFTER Kanto-CM
# is fully stopped.

FILESEXTRAPATHS:prepend := "${THISDIR}/files/update-manager:"

SRC_URI += "file://config.json \
           "

SRCREV="${AUTOREV}"

do_install:append() {
    install -m 0644 ${WORKDIR}/config.json ${D}${UM_CFG_DD}/update-manager/config.json
}
