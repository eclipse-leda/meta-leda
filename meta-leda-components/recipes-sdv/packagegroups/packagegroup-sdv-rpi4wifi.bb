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

SUMMARY = "SDV core WiFi packages for Raspberry Pi 4 Model B"
DESCRIPTION = "WiFi drivers and firmware for the Raspberry Pi 4 Model B BCM43455"

inherit packagegroup

RDEPENDS:${PN} = "\
    linux-firmware-bcm43455 \
"