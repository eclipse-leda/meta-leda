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

SUMMARY = "Airgapped Container Installer"
DESCRIPTION = "A service that installs pre-downloaded container images in a specified directory"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PREINSTALLED_CTR_IMAGES_DIR ??= "/var/constainers/images"

SRC_URI += " "

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/can-forward ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-device-info ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-health ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-motd ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/sdv-provision ${D}${bindir}
    install -m 0644 ${WORKDIR}/git/src/sh/sdv.conf ${D}/etc/sdv/

}