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

SUMMARY = "SDV application container images"
DESCRIPTION = "Package containing application container images to be installed on the target"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

CONTAINER_IMAGES = " \
    self-update-agent-container \
    hvac-service-example-container \
    vehicle-update-manager-container \
    seat-service-example-container \
    databroker-container \
    kuksa-gps-feeder-container \
"

CONTAINERS_TARGET_PATH ?= "/var/containers/images"

FILES:{PN} += "${CONTAINERS_TARGET_PATH}/*"

do_configure[depends] += "${@' '.join('%s:do_image_complete' % c for c in d.getVar('CONTAINER_IMAGES').split())}"

do_install() {
    install -d ${D}${CONTAINERS_TARGET_PATH}
    for IMG in ${CONTAINER_IMAGES}; do
        install -m 0644 ${DEPLOY_DIR_IMAGE}/${IMG}-${MACHINE}-oci.tar ${D}${CONTAINERS_TARGET_PATH}/
    done
}
