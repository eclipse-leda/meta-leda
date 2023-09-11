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

SUMMARY = "Covesa VSS Releases"

inherit packagegroup

RDEPENDS:${PN} = "\
    vss-release4 \
    vss-release3 \
    vss-release2 \
    vss-spec4 \
    vss-spec3 \
    vss-spec2 \
"
