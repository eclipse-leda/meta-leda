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

inherit bundle

# Must match to RAUCs system.conf files
RAUC_BUNDLE_COMPATIBLE = "Eclipse Leda (${MACHINE})"

# Use the newer Verity format for streaming update bundles
RAUC_BUNDLE_FORMAT = "verity"

# These are set in site.conf or in kas yaml files
# RAUC_KEY_FILE = "${THISDIR}/path/to/development-1.key.pem"
# RAUC_CERT_FILE = "${THISDIR}/path/to/development-1.cert.pem"

# Set RAUC bundle version to git tag or git commit in case no tag is available
def get_imageversion(d):
    # Default fallback is just the version of the distro ("2022")
    layerRev = d.getVar("DISTRO_VERSION")
    path = d.getVar("TOPDIR") or d.getVar("BUILD_DIR")
    try:
        (output, error) = bb.process.run("git describe --always --tags", cwd=path)
        layerRev = output.rstrip()
    except Exception as exc:
        bb.warn('Exception executing git, falling back to DISTRO_VERSION ${DISTRO_VERSION}', exc)
    d.setVar("VERSION_ID", layerRev)
    return layerRev

RAUC_BUNDLE_VERSION = "${@get_imageversion(d)}"
