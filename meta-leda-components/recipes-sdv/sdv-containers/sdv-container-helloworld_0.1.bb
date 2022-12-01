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

SUMMARY = "Embed SDV container archives into the system"
DESCRIPTION = "Pull the container images, save them in the rootfs."

inherit sdv-container-cache

SRC_URI += "file://README.txt \
            git://github.com/docker-library/hello-world/;protocol=https;branch=master"
SRCREV = "${AUTOREV}"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=4c512a2a4a9c2efd5e63d5865e1e9fcb"

# Define image to be pulled
SDV_IMAGE_REF="hello-world"
SDV_IMAGE_TAG="latest"

# Override container architecture. If not set, recipe tries autodetection for target machine architecture.
#CONTAINER_ARCH="arm64"

# This is a public available container
CONTAINER_REGISTRY_REQUIRES_AUTH = "0"

# Skip pre-caching of a container if target architecture does not exist
CONTAINER_SKIP_MISSING_ARCH="1"
