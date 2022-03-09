# /********************************************************************************
# * Copyright (c) 2021 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0
# *
# * SPDX-License-Identifier: EPL-2.0
# ********************************************************************************/

SUMMARY = "SDV Example Seat Service"
DESCRIPTION = "Example seat service"

inherit sdv

SRC_URI += "file://README.txt \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

# Define image to be pulled
SDV_IMAGE_REF="docker://ghcr.io/softwaredefinedvehicle/swdc-os-vehicleapi/seat_service:v0.6.0"

# Override container architecture. If not set, recipe tries autodetection for target machine architecture.
#CONTAINER_ARCH="arm64"

FILES_${PN} += "${datadir}/${PN}/README.txt \
                ${datadir}/${PN}/container-image \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"


