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
#
inherit kanto-auto-deployer

DESCRIPTION = "SDV Edge Example Containers"
SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

FILESEXTRAPATHS:prepend := "${THISDIR}/kanto-containers:"
SRC_URI:append = " file://example/hvac.json"
SRC_URI:append = " file://example/feedercan.json"
SRC_URI:append = " file://example_dev/seatservice.json"
SRC_URI:append = " file://example_dev/ota-client.json.disabled"

do_install:append() {
    install -d ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${WORKDIR}/example/hvac.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${WORKDIR}/example/feedercan.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}
# Under construction
#   install ${WORKDIR}/example/zipkin.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}

    install -d ${D}${KANTO_MANIFESTS_LOCAL_DEV_DIR}
    install ${WORKDIR}/example_dev/seatservice.json ${D}${KANTO_MANIFESTS_LOCAL_DEV_DIR}
    install ${WORKDIR}/example_dev/ota-client.json.disabled ${D}${KANTO_MANIFESTS_LOCAL_DEV_DIR}

# Under construction
#    install ${WORKDIR}/example_dev/otelcol-sdv-agent.json ${D}${KANTO_MANIFESTS_LOCAL_DEV_DIR}
#    install ${WORKDIR}/example_dev/otelcol-sdv-exporter.json ${D}${KANTO_MANIFESTS_LOCAL_DEV_DIR}

}

PACKAGES = "${PN}"

FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/hvac.json"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/feedercan.json"

FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DEV_DIR}"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DEV_DIR}/seatservice.json"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DEV_DIR}/ota-client.json.disabled"

# Under construction
# FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/zipkin.json"
# FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DEV_DIR}/otelcol-sdv-agent.json"
# FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DEV_DIR}/otelcol-sdv-exporter.json"
