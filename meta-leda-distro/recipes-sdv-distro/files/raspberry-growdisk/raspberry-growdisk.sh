#!/bin/sh
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
#
echo "Enlarging SD Card partition"

# Last partition on our image is supposed to be "6"
LAST_PARTITION_NUMBER=$(cat /proc/partitions | grep mmcblk0p | wc -l)

sgdisk /dev/mmcblk0 --move-second-header
parted --script /dev/mmcblk0 resizepart ${LAST_PARTITION_NUMBER} 100%
resize2fs /dev/mmcblk0p${LAST_PARTITION_NUMBER}
