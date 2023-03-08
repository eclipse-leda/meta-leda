# /********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
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

require sdv-rauc-bundle.inc

RAUC_BUNDLE_SLOTS = "rootfs"
RAUC_SLOT_rootfs = "sdv-image-minimal"
RAUC_SLOT_rootfs[fstype] = "ext4"

# Using block hash index improves performance of install
RAUC_SLOT_rootfs[adaptive] = "block-hash-index"
