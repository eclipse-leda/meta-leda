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

RAUC_BUNDLE_COMPATIBLE = "Eclipse Leda qemu86-64"

RAUC_BUNDLE_SLOTS = "rootfs_full rootfs_minimal"

# The name of the image to use, this could later be "sdv-core-image-minimal"
RAUC_SLOT_rootfs_full = "sdv-image-full"
RAUC_SLOT_rootfs_minimal = "sdv-image-minimal"

# Override the rootfs fstype for rauc, otherwise rauc will bundle "wic.qcow2" file,
# which cannot be used from within the guest. RAUC would fail on installing a "wic.qcow2" file.
RAUC_SLOT_rootfs_full[fstype] = "ext4"
RAUC_SLOT_rootfs_minimal[fstype] = "ext4"

# These are set in site.conf
# RAUC_KEY_FILE = "${THISDIR}/path/to/development-1.key.pem"
# RAUC_CERT_FILE = "${THISDIR}/path/to/development-1.cert.pem"