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

SUMMARY = "Eclipse Cyclone DDS Python binding"
HOMEPAGE = "https://cyclonedds.io"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ca2dafd1f07f3cd353d0454d3c4d9e80"

SRC_URI[sha256sum] = "f834962eabbdcdf4e9cd75cf87222f3c5ef22d9cb9e7ed651d9a8710fe984a30"

export CYCLONEDDS_HOME = "${STAGING_DIR_TARGET}/usr"

inherit pypi setuptools3

DEPENDS += " eclipse-cyclonedds"
RDEPENDS:${PN} += " eclipse-cyclonedds python3-rich-click python3-core python3-pip"

#RDEPENDS:${PN} += " \
#    ${PYTHON_PN}-twisted \
#    ${PYTHON_PN}-zopeinterface \
#    ${PYTHON_PN}-py-ubjson \
#    ${PYTHON_PN}-cbor2 \
#    ${PYTHON_PN}-u-msgpack-python \
#    ${PYTHON_PN}-lz4 \
#    ${PYTHON_PN}-snappy \
#    ${PYTHON_PN}-pyopenssl \
#    ${PYTHON_PN}-txaio \
#    ${PYTHON_PN}-six \
#"

#BBCLASSEXTEND = "native nativesdk"

