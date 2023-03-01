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

# Debug tweaks
IMAGE_FEATURES:append = " debug-tweaks"
IMAGE_FEATURES:append = " allow-empty-password"
IMAGE_FEATURES:append = " empty-root-password"

# The image dependencies are actually both types: build-time and run-time dependency
RDEPENDS:${PN} = " \
    sdv-image-full \
    sdv-image-minimal \
    sdv-image-rescue \
    sdv-image-data \
    sdv-rauc-bundle-full \
    sdv-rauc-bundle-minimal \
    sdv-rauc-bundle-rescue \
    "

DEPENDS = " \
    sdv-image-full \
    sdv-image-minimal \
    sdv-image-rescue \
    sdv-image-data \
    sdv-rauc-bundle-full \
    sdv-rauc-bundle-minimal \
    sdv-rauc-bundle-rescue \
    "

WKS_FILE_DEPENDS_BOOTLOADERS:remove = "grub-efi"

inherit core-image

# Ensure WICVARS are being built for each of the images before the WIC Image Type is trying to find them
do_image_wic[depends] += "sdv-image-rescue:do_rootfs_wicenv"
do_image_wic[depends] += "sdv-image-minimal:do_rootfs_wicenv"
do_image_wic[depends] += "sdv-image-full:do_rootfs_wicenv"
do_image_wic[depends] += "sdv-image-data:do_rootfs_wicenv"

# For qemu, we produce disk images in qcow2 format (faster and smaller than raw or ext4 images)
IMAGE_FSTYPES = " wic.qcow2"

# For scancode, we'll produce tar files of the rootfs
IMAGE_FSTYPES += " tar.bz2"

# Raspberry Pi needs a plain WIC file (not qcow2) to be flashed to SD-Card
IMAGE_FSTYPES:raspberrypi4-64 += " tar.bz2 wic.bz2 wic.bmap"

QB_DEFAULT_FSTYPE = "wic.qcow2"

# Fall back to ext4 for now, as wic for qemuarm does not yet contain u-boot
QB_ROOTFS_OPT:qemuarm = "-drive id=disk0,file=@ROOTFS@,if=none,format=qcow2 -device virtio-blk-device,drive=disk0"
QB_FSINFO:qemuarm = "wic:no-kernel-in-fs"

QB_ROOTFS_OPT:qemuarm64 = "-drive id=disk0,file=@ROOTFS@,if=none,format=qcow2 -device virtio-blk-device,drive=disk0"
QB_FSINFO:qemuarm64 = "wic:no-kernel-in-fs"

QB_KERNEL_ROOT = "/dev/vda4"
QB_DRIVE_TYPE = "/dev/vd"


# inherit overlayfs
# OVERLAYFS_MOUNT_POINT[data] = "/data/var/lib"
# OVERLAYFS_WRITABLE_PATHS[data] = "/var/lib"
# OVERLAYFS_QA_SKIP[varlib] = "mount-configured"

INCOMPATIBLE_LICENSE = "GPL-3.0* LGPL-3.0* AGPL-3.0*"
PACKAGE_EXCLUDE = "dosfstools"
