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

SDV_EXTERNAL_DEPENDS = "\
    sdv-k3s-config \
    sdv-core-bundle-pods \
    sdv-container-cloudagent \
    "

RDEPENDS:${PN} = "\
    ca-certificates \
    packagegroup-k3s-host \
    kernel-image \
    kernel-modules \
    openssh \
    openssh-sftp-server \
    rauc \
    sdv-core-utils \
    "

RDEPENDS:${PN} += "${@'${SDV_EXTERNAL_DEPENDS}' if d.getVar('INCLUDE_SDV') == '1' else ''}"
