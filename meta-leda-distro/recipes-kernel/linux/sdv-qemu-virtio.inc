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

SUMMARY = "SDV QEMU Virtual Device Support"
DESCRIPTION = "Enables support for QEMU emulated devices"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-yocto:"
SRC_URI:append:qemuarm = " file://qemu-virtio.cfg"
SRC_URI:append:qemuarm64 = " file://qemu-virtio.cfg"
SRC_URI:append:qemux86-64 = " file://qemu-virtio.cfg"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"
