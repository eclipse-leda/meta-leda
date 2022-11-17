# ********************************************************************************
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
# ********************************************************************************

DESCRIPTION = "Eclipse Cyclone DDS is a very performant and robust open-source DDS implementation." 
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ca2dafd1f07f3cd353d0454d3c4d9e80"

SRC_URI = "gitsm://github.com/eclipse-cyclonedds/cyclonedds.git;protocol=https;branch=releases/0.10.x"
SRCREV = "9995905bce6c4cf9f740d6438bbf7fcfd1c83dfd"
PV = "0.10.2"

S = "${WORKDIR}/git"

# NOTE: iceoryx dependency not included for simplicity - there's no recipe for that, yet.
# DEPENDS = "bison-native openssl eclipse-iceoryx"
# EXTRA_OECMAKE = " -DENABLE_SHM=ON"

DEPENDS = "bison-native openssl"

RDEPENDS:${PN} = "openssl"

INSANE_SKIP:${PN} += "dev-so"
FILES:${PN} += "${libdir}/lib*.so"
FILES_SOLIBSDEV = ""

inherit cmake
