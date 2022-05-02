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

RAUC_BUNDLE_COMPATIBLE = "Eclipse Kastro qemu86-64"

RAUC_BUNDLE_SLOTS = "rootfs"
RAUC_SLOT_rootfs = "core-image-minimal"

# These are set in site.conf
# RAUC_KEY_FILE = "${THISDIR}/path/to/development-1.key.pem"
# RAUC_CERT_FILE = "${THISDIR}/path/to/development-1.cert.pem"