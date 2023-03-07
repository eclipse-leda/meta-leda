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

SUMMARY = "SDV Vehicle Update Manager container"
DESCRIPTION = "Docker container of the SDV Vehicle Update Manager"

inherit sdv-container-cache

SRC_URI += "file://README.txt \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Define image to be pulled
SDV_IMAGE_REF="ghcr.io/eclipse-leda/leda-contrib-vehicle-update-manager/vehicleupdatemanager"
SDV_IMAGE_TAG="main-1d8dca55a755c4b3c7bc06eabfa06ad49e068a48"

# Override container architecture. If not set, recipe tries autodetection for target machine architecture.
#CONTAINER_ARCH="arm64"

