# /********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
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

SUMMARY = "SDV Misc Kernel WiFi Configuration"
DESCRIPTION = "Modify some general WiFi Kernel defaults and firmware drivers"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-yocto:"
SRC_URI:append = " file://kernel-crypto.cfg"
SRC_URI:append:raspberrypi4-64 = " file://kernel-wifi-rpi4-64.cfg"
SRC_URI:append:qemuarm = " file://kernel-wifi-simulate.cfg"
SRC_URI:append:qemuarm64 = " file://kernel-wifi-simulate.cfg"
SRC_URI:append:qemux86-64 = " file://kernel-wifi-simulate.cfg"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
#LIC_FILES_CHKSUM = "file://LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"
