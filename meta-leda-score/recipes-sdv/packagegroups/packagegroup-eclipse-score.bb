# ********************************************************************************
# * Copyright (c) 2024 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************

SUMMARY = "Eclipse S-Core packages"
DESCRIPTION = "Packages for the Eclipse S-CORE (Safe Open Vehicle Core) automotive \
middleware platform. S-CORE provides a modular, safety-compliant, service-oriented \
middleware layer for high-performance ECUs in Software-Defined Vehicles."

inherit packagegroup

RDEPENDS:${PN} = "\
    eclipse-score-baselibs \
    eclipse-score-communication \
    eclipse-score-feo \
    "
