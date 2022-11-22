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
#
# Adds additional fields to /etc/os-release
# The fields are semantically defined by https://www.freedesktop.org/software/systemd/man/os-release.html
#
# Example output in ./tmp/sysroots-components/all/os-release/usr/lib/os-release:
# 
# ID=leda
# NAME="Eclipse Leda"
# VERSION="2022 (Hockenheim)"
# VERSION_ID=v0.0.23-31-g64893dc
# PRETTY_NAME="Eclipse Leda 2022 (Hockenheim)"
# VERSION_CODENAME="Hockenheim"
# IMAGE_ID="None"
# IMAGE_MACHINE_ID="None"
# IMAGE_VERSION="v0.0.23-31-g64893dc"
# VERSION_ID=v0.0.23-31-g64893dc
# BUILD_ID="20220901131248"
#

inherit features_check

REQUIRED_DISTRO_FEATURES = "sdv"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

DEPENDS = "git-native"

# Always regenerate the /etc/os-release file to keep the metadata up to date
do_compile[nostamp]="1"

def exec_git_run(cmd, path):
    (output, error) = bb.process.run(cmd, cwd=path)
    return output.rstrip()

python do_compile:prepend() {
    if bb.utils.contains('DISTRO_FEATURES', 'sdv', True, False, d):
        # Add additional fields to /etc/os-release
        fields = set((d.getVar('OS_RELEASE_FIELDS') or "").split())
        fields.add("VERSION_CODENAME")
        fields.add("IMAGE_VERSION")
        fields.add("VERSION_ID")
        fields.add("BUILD_ID")
        d.setVar('OS_RELEASE_FIELDS', ' '.join(sorted(fields)))

        # Default fallback is just the version of the distro ("2022")
        layerRev = d.getVar("DISTRO_VERSION")
        path = d.getVar("TOPDIR") or d.getVar("BUILD_DIR")
        try:
            ver = exec_git_run("git describe --always --tags", path)
            layerRev = ver
        except Exception as exc:
            bb.warn('Exception executing git, falling back to DISTRO_VERSION')

        d.setVar("VERSION_CODENAME", d.getVar("DISTRO_CODENAME"))
        d.setVar("IMAGE_VERSION", layerRev)
        d.setVar("VERSION_ID", layerRev)
}
