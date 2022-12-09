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

DESCRIPTION = "Kanto Default Containers"
SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Will be changed for sua.json as well and be separated in a different recipe
SUA_DATA_DIR = "/data_temp/selfupdates"

do_install:append() {
    install -d ${D}${KANTO_MANIFESTS_DIR}
    install ${THISDIR}/kanto-containers/databroker.json ${D}${KANTO_MANIFESTS_DIR}
    install ${THISDIR}/kanto-containers/seatservice.json ${D}${KANTO_MANIFESTS_DIR}
    install ${THISDIR}/kanto-containers/vum.json ${D}${KANTO_MANIFESTS_DIR}
    mkdir -p ${D}${SUA_DATA_DIR}
    install ${THISDIR}/kanto-containers/sua.json ${D}${KANTO_MANIFESTS_DIR}
}

PACKAGES = "${PN}"
FILES:${PN} += "${KANTO_MANIFESTS_DIR}/databroker.json"
FILES:${PN} += "${KANTO_MANIFESTS_DIR}/seatservice.json"
FILES:${PN} += "${KANTO_MANIFESTS_DIR}/vum.json"
FILES:${PN} += "${SUA_DATA_DIR}"
FILES:${PN} += "${KANTO_MANIFESTS_DIR}/sua.json"

