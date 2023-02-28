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

SUMMARY = "SDV container images to demonstrate CycloneDDS"
DESCRIPTION ="The resulting image contains all the python examples from the CycloneDDS project"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

require ../images/sdv-image-container.bb

IMAGE_INSTALL += " \
    busybox \
    python3-cyclonedds \
    eclipse-cyclonedds-examples \
    "

OCI_IMAGE_TAG = "cyclonedds-example:${PV}"
