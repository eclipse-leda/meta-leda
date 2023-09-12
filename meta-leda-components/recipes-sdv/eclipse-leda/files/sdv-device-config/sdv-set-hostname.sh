#!/bin/sh
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

DEVICE_ID=$(sdv-generate-deviceid.sh)

if [ -n "${DEVICE_ID}" ];
then
    HOST_NAME="leda-${DEVICE_ID}"
    hostname "${HOST_NAME}"
    echo "${HOST_NAME}" > /etc/hostname
    sed -i "s/^127.0.1.1.*/127.0.0.1 ${HOST_NAME}/" /etc/hosts
else
    echo "Device ID not set, aborting!" >&2
    exit 1
fi
