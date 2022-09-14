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
RAUC_BUNDLE_COMPATIBLE = "Eclipse Leda"

# To build multiple/different slots, add them here and then add RAUC_SLOT_slotname to map to BitBake images
# RAUC_BUNDLE_SLOTS = "rootfs_full rootfs_minimal"
# RAUC_SLOT_rootfs_full = "sdv-image-full"
# RAUC_SLOT_rootfs_minimal = "sdv-image-minimal"

# For now, we only have one "rootfs" slot and it will always be a "sdv-image-minimal" to save download size.
RAUC_BUNDLE_SLOTS = "rootfs"

# The name of the image to use, this could later be "sdv-core-image-minimal"
RAUC_SLOT_rootfs = "sdv-image-minimal"

# Override the rootfs fstype for rauc, otherwise rauc will bundle "wic.qcow2" file,
# which cannot be used from within the guest. RAUC would fail on installing a "wic.qcow2" file.
RAUC_SLOT_rootfs[fstype] = "ext4"

# Set RAUC bundle version to git tag or git commit in case no tag is available
RAUC_BUNDLE_VERSION = "${@bb.process.run('GIT_CEILING_DIRECTORIES= git describe --tags --always')[0]}"

# These are set in site.conf
# RAUC_KEY_FILE = "${THISDIR}/path/to/development-1.key.pem"
# RAUC_CERT_FILE = "${THISDIR}/path/to/development-1.cert.pem"
