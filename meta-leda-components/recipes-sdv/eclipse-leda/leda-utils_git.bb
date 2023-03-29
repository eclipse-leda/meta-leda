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
DESCRIPTION = "Core shell scripts for Eclipse Leda"

SRC_URI = "git://github.com/eclipse-leda/leda-utils;branch=main;protocol=https"
SRCREV = "67ed015d636259591695e657a12fea3fa3c8b1e3"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

RDEPENDS:${PN} += "mosquitto-clients"

do_install() {
    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}/sdv

    install -m 0755 ${WORKDIR}/git/src/sh/can-forward ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-device-info ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-health ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-help ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-motd ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-provision ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-ctr-exec ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-kanto-ctl ${D}${bindir}

    install -m 0644 ${WORKDIR}/git/src/sh/sdv.conf ${D}/etc/sdv/

    install -d ${D}${sysconfdir}/profile.d
    ln -sf ${bindir}/sdv-motd ${D}${sysconfdir}/profile.d/sdv-motd.sh
}

FILES_${PN} += "${bindir}/can-forward"
FILES_${PN} += "${bindir}/sdv-device-info"
FILES_${PN} += "${bindir}/sdv-health"
FILES_${PN} += "${bindir}/sdv-help"
FILES_${PN} += "${bindir}/sdv-motd"
FILES_${PN} += "${bindir}/sdv-provision"
FILES_${PN} += "${bindir}/sdv-ctr-exec"
FILES_${PN} += "${bindir}/sdv-kanto-ctl"
FILES_${PN} += "/etc/sdv/sdv.conf"

PACKAGES = "${PN}"
