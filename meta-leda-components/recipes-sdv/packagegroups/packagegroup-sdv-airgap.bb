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

SUMMARY = "SDV Air-Gap Installation"
DESCRIPTION = "Pre-Load Container Images for Offline installation"

inherit packagegroup

RDEPENDS:${PN} += "\
    sdv-container-cloudagent \
    sdv-container-databroker \
    sdv-container-feedercan \
    sdv-container-otelagent \
    sdv-container-otelexporter \
    sdv-container-selfupdateagent \
    sdv-container-vehicleupdatemanager \
    sdv-containers-dapr \
    rancher-container-coredns \
    rancher-container-klipper \
    rancher-container-local-path-provisioner \
    rancher-container-metrics \
    rancher-container-traefik \
    sdv-kuksa-val-databroker \
    "


