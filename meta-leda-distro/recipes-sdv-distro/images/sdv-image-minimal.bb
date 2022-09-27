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

SUMMARY = "A minimal quickstart image with only core SDV packages installed."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

# SDV Minimal
IMAGE_INSTALL:append = " packagegroup-sdv-core"

IMAGE_LINGUAS = " "

LICENSE = "EPL"

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
QB_DRIVE_TYPE="/dev/vd"

QB_KERNEL_CMDLINE_APPEND = "console=ttyS0,115200 net.ifnames=0 panic=5 ip=dhcp ip=192.168.7.2::192.168.7.1:255.255.255.0 rootwait"
