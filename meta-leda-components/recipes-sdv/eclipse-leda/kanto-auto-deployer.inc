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

inherit systemd kanto-auto-deployer

SUMMARY = "Kanto Auto Deployer via a unix socket"
DESCRIPTION = "Automated deployment of Kanto Container Management Manifests"
HOMEPAGE = "https://github.com/eclipse-leda/leda-utils"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_PACKAGES = "${@bb.utils.contains('DISTRO_FEATURES','systemd','${PN}','',d)}"
SYSTEMD_SERVICE:${PN} = "${@bb.utils.contains('DISTRO_FEATURES','systemd','kanto-auto-deployer.service','',d)}"

KANTO_SERVICE_DIR = "${systemd_unitdir}/system"

FILES:${PN} += "${systemd_unitdir}/system/kanto-auto-deployer.service"
FILES:${PN} += "${KANTO_MANIFESTS_DIR}"

do_install:append() {
    install_service
}

install_service() {
    install -d ${D}${KANTO_SERVICE_DIR}
    install -m 0644 ${THISDIR}/files/kanto-auto-deployer.service.template ${D}${KANTO_SERVICE_DIR}/kanto-auto-deployer.service

    if [ -z ${KANTO_MANIFESTS_DIR} ]; then
        bbfatal "KANTO_MANIFESTS_DIR must not be empty, please set in your local.conf"
    fi

    # fill in the kanto auto deployer service template with the result configurations
    sed -e 's,@KD_BIN_DD@,${bindir},g' \
        -e 's,@KD_CFG_DD@,${KANTO_MANIFESTS_DIR},g' \
    -i ${D}${KANTO_SERVICE_DIR}/kanto-auto-deployer.service

    install -d ${D}${KANTO_MANIFESTS_DIR}
}
