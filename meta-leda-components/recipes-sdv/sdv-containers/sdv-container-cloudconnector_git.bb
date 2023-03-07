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

SUMMARY = "SDV Cloud Connector container"
DESCRIPTION = "Docker container of the Eclipse Kanto Cloud Connector"

inherit sdv-container-cache

SRC_URI += "file://README.txt \
            git://github.com/eclipse-leda/leda-contrib-cloud-connector;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

# Define image to be pulled
SDV_IMAGE_REF="ghcr.io/eclipse-leda/leda-contrib-cloud-connector/cloudconnector"
SDV_IMAGE_TAG="main-47c01227a620a3dbd85b66e177205c06c0f7a52e"

# Override container architecture. If not set, recipe tries autodetection for target machine architecture.
#CONTAINER_ARCH="arm64"

# Skip pre-caching of a container if target architecture does not exist
CONTAINER_SKIP_MISSING_ARCH="0"
