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

SUMMARY = "UDS Server"
DESCRIPTION = "Unified Diagnostic Services (UDS) Server"
HOMEPAGE = "https://github.com/zombieCraig/uds-server"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2c1c00f9d3ed9e24fa69b932b7e7aff2"

SRC_URI += "git://github.com/zombieCraig/${BPN}.git;protocol=https;branch=master"
SRCREV = "231227e2785b99c1bc81e78da848750e67d30097"
SRC_URI[sha256sum] = "a6fbc86ed3c6d0758b256fd00f18961644f0ed1bdc7784cad828f163e76051bc"
S = "${WORKDIR}/git"
TARGET_CC_ARCH += "${LDFLAGS}"

do_compile() {
    ${CC} ${S}/${BPN}.c ${TARGET_CC_ARCH} -o ${S}/${BPN}
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/${BPN} ${D}${bindir}
}

FILES:${PN} += "${bindir}/${BPN}"
