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

SUMMARY = "SDV core packages"
DESCRIPTION = "Packages required to set up a basic working SDV system"

inherit packagegroup

RDEPENDS:${PN} = "\
    packagegroup-k3s-host \
    sdv-k3s-config \
    kernel-modules \
    ca-certificates \
    rauc \
    rauc-hawkbit-updater \
    sdv-dapr-binaries \
    sdv-initialize-dapr-first-boot \
    sdv-helm-binaries \
    sdv-core-bundle-pods \
    sdv-container-cloudagent \
    sdv-container-databroker \
    sdv-container-otelagent \
    sdv-container-otelexporter \    
    sdv-container-vehicleupdatemanager \
    kernel-modules \
    kanto-container-management \
    kanto-file-upload \
    kanto-software-update \
    kanto-suite-connector \
    "
