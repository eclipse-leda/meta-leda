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
LICENSE = "Apache-2.0"

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"
IMAGE_INSTALL:append = " kernel-image kernel-modules"

# SDV Full Image contains all package groups

IMAGE_INSTALL:append = " packagegroup-sdv-core"
IMAGE_INSTALL:append = " packagegroup-sdv-additions"
IMAGE_INSTALL:append = " packagegroup-sdv-tools"
IMAGE_INSTALL:append = " packagegroup-sdv-examples"

IMAGE_LINGUAS = " "

# IMAGE_FEATURES:append = " read-only-rootfs"
# IMAGE_INSTALL:append = " volatile-binds"

# Debug tweaks
IMAGE_FEATURES:append = " debug-tweaks"
IMAGE_FEATURES:append = " allow-empty-password"
IMAGE_FEATURES:append = " empty-root-password"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"

# Fall back to ext4 for now, as wic for qemuarm does not yet contain u-boot
#QB_ROOTFS_OPT = "-drive id=disk0,file=@ROOTFS@,if=none,format=raw -device virtio-blk-device,drive=disk0"
QB_FSINFO = "wic:no-kernel-in-fs"

QB_KERNEL_ROOT = "/dev/vda"
QB_DRIVE_TYPE = "/dev/vd"
