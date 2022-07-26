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

SUMMARY = "SDV core-native packages"
DESCRIPTION = "Packages required to set up a basic working SDV system without Kubernetes"

inherit packagegroup
SDV_EXTERNAL_DEPENDS = "\
    sdv-cloud-connector \
    "

RDEPENDS:${PN} = "\
    ca-certificates \
    kernel-modules \
    kernel-image \
    rauc "

RDEPENDS:${PN} += "${@bb.utils.contains("DISTRO_FEATURES", "sdv", "${SDV_EXTERNAL_DEPENDS}", "", d)}"
