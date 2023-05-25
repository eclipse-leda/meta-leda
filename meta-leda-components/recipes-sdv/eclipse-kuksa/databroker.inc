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

DESCRIPTION = "Efficient in-vehicle signal broker written in RUST"


PACKAGES =+ "${PN}-vss-core"
SUMMARY:${PN}-vss-core = "Covesa VSS Releases"

FILES:${PN}-vss-core = "${datadir}/vss-core"

do_install:append() {
    install -d ${D}${datadir}/vss-core
    install -m 0644 ${S}/data/vss-core/*.json ${D}${datadir}/vss-core
}