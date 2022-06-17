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

SUMMARY = "SDV core additional packages"
DESCRIPTION = "Packages required to set up a basic working demo SDV system, but may contain temporary or additional packages"

inherit packagegroup

SDV_EXTERNAL_DEPENDS = "\
    sdv-container-databroker \
    sdv-dapr-binaries \
    sdv-initialize-dapr-first-boot \
    sdv-helm-binaries \
    "

RDEPENDS:${PN} = "\
    kanto-container-management \
    kanto-file-upload \
    kanto-software-update \
    kanto-suite-connector \
    rauc-hawkbit-updater \
    "

RDEPENDS:${PN} += "${@bb.utils.contains("DISTRO_FEATURES", "sdv", "${SDV_EXTERNAL_DEPENDS}", "", d)}"
