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

SUMMARY = "Python examples for Eclipse Cyclone DDS"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=ca2dafd1f07f3cd353d0454d3c4d9e80"

SRC_URI = "gitsm://github.com/eclipse-cyclonedds/cyclonedds-python.git;protocol=https;branch=releases/0.10.x"
SRCREV = "9cec1189a3d5a1407851dfe1f40899dd4a67f52d"
PV = "0.10.2"

S = "${WORKDIR}/git"
CYCLONEDDS_EXAMPLE_DIR = "/usr/local/lib/cyclonedds-examples"

RDEPENDS_${PN} += "python3-cyclonedds"

do_install () {
    install -d ${D}${CYCLONEDDS_EXAMPLE_DIR}
    cp -R ${S}/examples/* ${D}${CYCLONEDDS_EXAMPLE_DIR}
}

FILES:${PN} = "${CYCLONEDDS_EXAMPLE_DIR}/"
