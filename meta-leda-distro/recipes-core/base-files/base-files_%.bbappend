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

inherit features_check

REQUIRED_DISTRO_FEATURES = "sdv"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# Ensure file gets updated on each build
do_install[nostamp] = "1"
do_install_basefilesissue[nostamp] = "1"

# Create some additionally needed directories in root filesystem:
# - /data for Kanto Container Management
# - /grubenv to hold bootloader (GRUB or U-Boot) environment and RAUC status information
dirs755 += "/data"
dirs755 += "/grubenv"
dirs755 += "/rescue"

DEPENDS = "git-native"

# Overwrite /etc/issue with a custom version to show git tag
do_install_basefilesissue:append() { 
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sdv', 'true', 'false', d)}; then
        # Default fallback is just the version of the distro ("2022")
        LAYER_REV="${DISTRO_VERSION}"

        # If we have a git source repo, then use git specific
        # tag description ("v0.1-3-abcd1234")
        if git describe --tags > /dev/null;
        then
            LAYER_REV=$(git describe --tags)
        fi
        printf "${DISTRO_NAME} ${LAYER_REV}\n" > ${D}${sysconfdir}/issue
        printf "${DISTRO_NAME} ${LAYER_REV}\n" > ${D}${sysconfdir}/issue.sdv
    fi
}
