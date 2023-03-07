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

SUMMARY = "SDV pre-downloaded containers"
DESCRIPTION = "Pre-downloaded (airgapped) containers that would be installed in the image by default"

inherit packagegroup

RDEPENDS:${PN} = "\
    sdv-kuksa-val-databroker \
    sdv-container-seatservice \
    sdv-container-selfupdateagent \
    sdv-container-cloudconnector \
    sdv-container-vehicleupdatemanager  \
    sdv-container-hvacservice \
    sdv-container-feedercan \
 "
