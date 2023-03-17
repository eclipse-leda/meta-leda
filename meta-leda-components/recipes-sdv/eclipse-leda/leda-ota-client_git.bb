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

SUMMARY = "OTA Client for connection with Eclipse Backend Function Bindigs"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

PGM_NAME = "ota-client"

SRC_URI += " \
    file://${PGM_NAME}/ota-config.json \
    file://${PGM_NAME}/README-ota-client.txt \
    file://${PGM_NAME}/container-management-config.json.template \
"

S = "${WORKDIR}/${PGM_NAME}"

FILES:${PN} += " \
    ${sysconfdir}/${PGM_NAME} \
    ${sysconfdir}/${PGM_NAME}/certs \
    ${sysconfdir}/${PGM_NAME}/ota-config.json \
    ${sysconfdir}/${PGM_NAME}/README-ota-client.txt \
    ${sysconfdir}/${PGM_NAME}/container-management-config.json.template \
"

do_install() {
    # Install templat configuration file
    install -d ${D}${sysconfdir}/${PGM_NAME}
    install -m 755 ${B}/ota-config.json ${D}${sysconfdir}/${PGM_NAME}
    install -m 755 ${B}/README-ota-client.txt ${D}${sysconfdir}/${PGM_NAME}
    install -m 755 ${B}/container-management-config.json.template ${D}${sysconfdir}/${PGM_NAME}

    # Create empty folder for device certificates
    install -d ${D}${sysconfdir}/${PGM_NAME}/certs
}
