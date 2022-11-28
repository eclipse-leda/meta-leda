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

SUMMARY = "SDV Core Utilities"
DESCRIPTION = "Core shell scripts"

SRC_URI = "git://github.com/eclipse-leda/leda-utils;branch=main;protocol=https"
SRCREV = "586ef6da1b841efd76fb06b9821bae4277e325e0"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Runtime Dependencies
RDEPENDS:${PN} += " bash"

# Force to refetch every time
do_fetch[nostamp] = "1"
do_install[nostamp] = "1"

# Skip the compilation tasks
#do_compile[noexec] = "1"
#do_configure[noexec] = "1"
#do_package_qa[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}/etc/sdv
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/can-forward ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/sdv-device-info ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/sdv-health ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/sdv-motd ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/sdv-provision ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/sdv.conf ${D}/etc/sdv/
}

FILES_${PN} += "${bindir}/can-forward"
FILES_${PN} += "${bindir}/sdv-device-info"
FILES_${PN} += "${bindir}/sdv-health"
FILES_${PN} += "${bindir}/sdv-motd"
FILES_${PN} += "${bindir}/sdv-provision"
FILES_${PN} += "/etc/sdv/sdv.conf"

PACKAGES = "${PN}"
