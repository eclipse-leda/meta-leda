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
AG_SERVICE_DIR = "${systemd_unitdir}/system"


SRC_URI += "    file://airgap-ctr-installer \ 
                file://airgap-container-installer.service.template
           "


install_service() {
    install -d ${D}${AG_SERVICE_DIR}
    install -m 0644 ${THISDIR}/airgap-container-installer.service.template ${D}${AG_SERVICE_DIR}/airgap-container-installer.service

    sed -e 's,@AG_CFG_DD@,${bindir},g' \
        -e 's,@AG_CFG_DD@,${PREINSTALLED_CTR_IMAGES_DIR},g' \
    -i ${D}${AG_SERVICE_DIR}/airgap-container-installer.service
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/git/src/sh/airgap-ctr-installer ${D}${bindir}
    install_service
}