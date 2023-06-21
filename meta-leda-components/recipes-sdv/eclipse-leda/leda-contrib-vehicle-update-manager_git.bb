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

SUMMARY = "SDV Vehicle Update Manager"
DESCRIPTION = "This is the vehicle update manager component used in the Software Defined Vehicle EDGE stack."
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://${GO_IMPORT};protocol=https;branch=main"
SRCREV = "1d8dca55a755c4b3c7bc06eabfa06ad49e068a48"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-leda/leda-contrib-vehicle-update-manager"

S = "${WORKDIR}/git"

inherit go-mod

do_compile[network] = "1"

do_install:append () {
    install -d ${D}${localstatedir}/lib/updatemanagerd
	mv ${D}${bindir}/daemon ${D}${bindir}/updatemanagerd
}
