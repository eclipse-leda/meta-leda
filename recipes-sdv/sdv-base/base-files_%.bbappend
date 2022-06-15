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

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# Ensure file gets updated on each build
do_install[nostamp] = "1"
do_install_basefilesissue[nostamp] = "1"

# Create some additionally needed directories in root filesystem:
# - /data for RAUC Statusfile
# - /grubenv to hold GRUB Bootloader status information
# - /k3s is mount point for separate partition for Rancher K3S data-dir
dirs755 += "/data"
dirs755 += "/grubenv"
dirs755 += "/rescue"

# Overwrite /etc/issue with a custom version of it
do_install_basefilesissue:append() {
    LAYER_REV=$(git describe --tags)
    printf "${DISTRO_NAME} ${LAYER_REV}\n" > ${D}${sysconfdir}/issue
}
