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
S = "${UNPACKDIR}"
SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

FILESEXTRAPATHS:prepend := "${THISDIR}/kanto-containers:"
SRC_URI:append = " file://example/hvac.json"
SRC_URI:append = " file://example/feedercan.json"
SRC_URI:append = " file://example/feedergps.json"
SRC_URI:append = " file://example/seatservice.json"
SRC_URI:append = " file://example/node-red.json"
SRC_URI:append = " file://example/seatadjuster-app.json.disabled"
SRC_URI:append = " file://example/ota-client.json.disabled"
SRC_URI:append = " file://example/zipkin.json.disabled"
SRC_URI:append = " file://example/otelcol-sdv-agent.json.disabled"
SRC_URI:append = " file://example/otelcol-sdv-exporter.json.disabled"

do_install:append() {
    install -d ${D}${KANTO_MANIFESTS_LOCAL_DIR}

    # Activated Containers
    install ${UNPACKDIR}/example/hvac.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${UNPACKDIR}/example/feedercan.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${UNPACKDIR}/example/seatservice.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}

    # Do not auto-deploy images that are not part of the airgap image cache.
    if ${@bb.utils.contains('DISTRO_FEATURES', 'airgap-containers', 'false', 'true', d)}; then
        install ${UNPACKDIR}/example/feedergps.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}
        install ${UNPACKDIR}/example/node-red.json ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    fi

    # Deactivated Containers
    install ${UNPACKDIR}/example/seatadjuster-app.json.disabled ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${UNPACKDIR}/example/ota-client.json.disabled ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${UNPACKDIR}/example/zipkin.json.disabled ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${UNPACKDIR}/example/otelcol-sdv-agent.json.disabled ${D}${KANTO_MANIFESTS_LOCAL_DIR}
    install ${UNPACKDIR}/example/otelcol-sdv-exporter.json.disabled ${D}${KANTO_MANIFESTS_LOCAL_DIR}

}

PACKAGES = "${PN}"

FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}"

FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/hvac.json"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/feedercan.json"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/feedergps.json"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/seatservice.json"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/node-red.json"

FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/seatadjuster-app.json.disabled"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/ota-client.json.disabled"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/zipkin.json.disabled"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/otelcol-sdv-agent.json.disabled"
FILES:${PN} += "${KANTO_MANIFESTS_LOCAL_DIR}/otelcol-sdv-exporter.json.disabled"
