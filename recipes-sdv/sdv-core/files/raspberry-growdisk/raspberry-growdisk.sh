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

parted /dev/mmcblk0 resizepart 4 100%
resize2fs /dev/mmcblk0p2

echo "raspberry-growdisk.service was disabled"
