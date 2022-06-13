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

SUMMARY = "SDV CAN Feeder"
DESCRIPTION = "Feed CAN bus simulation into the SDV Data Broker"

inherit sdv-container-cache

SRC_URI += "file://README.txt \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

# Define image to be pulled
SDV_IMAGE_REF="ghcr.io/softwaredefinedvehicle/swdc-os-vehicleapi/feeder_can"
SDV_IMAGE_TAG="v0.17.0"

# Override container architecture. If not set, recipe tries autodetection for target machine architecture.
#CONTAINER_ARCH="arm64"

# Skip pre-caching of a container if target architecture does not exist
CONTAINER_SKIP_MISSING_ARCH="1"

