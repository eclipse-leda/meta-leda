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

SUMMARY = "SDV examples packages"
DESCRIPTION = "Packages SDV examples (Seat Service)"

inherit packagegroup

# Temporarily disabled packages:
# - sdv-container-seatapp
SDV_EXTERNAL_DEPENDS = "\
    sdv-container-seatservice \
    "
RDEPENDS:${PN} = "\
    can-utils \
    can-utils-access \
    can-utils-isotp \
    can-utils-j1939 \
    can-utils-cantest \
    can-utils-slcan \
    can-utils-log \
    "
RDEPENDS:${PN} += "${@bb.utils.contains("DISTRO_FEATURES", "sdv", "${SDV_EXTERNAL_DEPENDS}", "", d)}"

KERNEL_MODULE_AUTOLOAD += "can"
KERNEL_MODULE_AUTOLOAD += "vcan"
KERNEL_MODULE_AUTOLOAD += "vxcan"
