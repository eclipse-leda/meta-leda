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
#

DESCRIPTION = "SDV Example Bluprints that can be deployed with blueprints selector."

SRC_URI += "git://github.com/eclipse-leda/leda-utils.git;protocol=https;nobranch=1;branch=main;name=bluerpintsSelectorRepo;destsuffix=selector"
SRCREV_bluerpintsSelectorRepo = "1f933716a8500c9f464537d60dfd33c8cdc0a850"
SRCREV_FORMAT = "bluerpintsSelectorRepo_"
BLUEPRINTS_SELECTOR_EXAMPLES = "${WORKDIR}/selector/src/rust/blueprint-selector/example_blueprints" 

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

BLUEPRINTS_LOCAL_DIR ??= "/var/containers/blueprints"
BLUEPRINTS_LOCAL_DIR[doc] = "Specifies the location in the image where the blueprints will be installed. \
                             This should, in general, match with the location in the blueprint-selector.inc recipe"

do_install:append() {
    install -d ${D}${BLUEPRINTS_LOCAL_DIR}

    install ${BLUEPRINTS_SELECTOR_EXAMPLES}/leda_default.blueprint.json ${D}${BLUEPRINTS_LOCAL_DIR}
    install ${BLUEPRINTS_SELECTOR_EXAMPLES}/fleet_management.blueprint.json ${D}${BLUEPRINTS_LOCAL_DIR}
    install ${BLUEPRINTS_SELECTOR_EXAMPLES}/databroker_only.blueprint.json ${D}${BLUEPRINTS_LOCAL_DIR}
}

PACKAGES = "${PN}"

FILES:${PN} += "${BLUEPRINTS_LOCAL_DIR}"
FILES:${PN} += "${BLUEPRINTS_LOCAL_DIR}/leda_default.blueprint.json"
FILES:${PN} += "${BLUEPRINTS_LOCAL_DIR}/fleet_management.blueprint.json"
FILES:${PN} += "${BLUEPRINTS_LOCAL_DIR}/databroker_only.blueprint.json"


