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
LICENSE = "EPL"

RDEPENDS:${PN} = "sdv-image-full sdv-image-minimal sdv-image-rescue sdv-rauc-bundle"

inherit core-image

# Ensure efi-boot.vfat is built
# Must only be run for qemux86_86
#do_image_wic[depends] += "boot-image:do_deploy"

# Ensure WICVARS are being built for each of the images before the WIC Image Type is trying to find them
do_image_wic[depends] += "sdv-image-rescue:do_rootfs_wicenv"
do_image_wic[depends] += "sdv-image-minimal:do_rootfs_wicenv"
do_image_wic[depends] += "sdv-image-full:do_rootfs_wicenv"

IMAGE_FSTYPES = "wic.qcow2"
QB_DEFAULT_FSTYPE = "wic.qcow2"

# Fall back to ext4 for now, as wic for qemuarm does not yet contain u-boot
QB_ROOTFS_OPT:qemuarm = "-drive id=disk0,file=@ROOTFS@,if=none,format=qcow2 -device virtio-blk-device,drive=disk0"
QB_FSINFO:qemuarm = "wic:no-kernel-in-fs"

QB_ROOTFS_OPT:qemuarm64 = "-drive id=disk0,file=@ROOTFS@,if=none,format=qcow2 -device virtio-blk-device,drive=disk0"
QB_FSINFO:qemuarm64 = "wic:no-kernel-in-fs"

# Must be in sync with:
#  GRUB Config: meta-sdv/recipes-bsp/grub/files/grub.cfg
#  WIC Kickstarter File: build-sdv-arm-qemu/wic/qemuarm-grub.wks
QB_KERNEL_ROOT = "/dev/vda4"
QB_DRIVE_TYPE="/dev/vd"
