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

SUMMARY = "A full quickstart image with all features and convenience tools enabled."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

# SDV Full Image contains all package groups

IMAGE_INSTALL:append = " packagegroup-sdv-core"
IMAGE_INSTALL:append = " packagegroup-sdv-additions"
IMAGE_INSTALL:append = " packagegroup-sdv-tools"
IMAGE_INSTALL:append = " packagegroup-sdv-examples"

IMAGE_LINGUAS = " "

LICENSE = "EPL"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
