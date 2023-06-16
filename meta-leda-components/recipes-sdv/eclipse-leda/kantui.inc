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

SUMMARY = "KantUI"
DESCRIPTION = "A TUI for Kanto CM that allows easier management of deployed containers."
HOMEPAGE = "https://github.com/eclipse-leda/leda-utils"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

do_install:append() {
    install -d ${D}${sysconfdir}/kantui
    install -m 0664 "${S}/${CARGO_SRC_DIR}/kantui_conf.toml" "${D}${sysconfdir}/kantui/kantui_conf.toml"
}
