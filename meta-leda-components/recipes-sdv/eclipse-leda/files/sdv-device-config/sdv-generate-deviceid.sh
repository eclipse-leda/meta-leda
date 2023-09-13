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
# shellcheck disable=SC2010

DEVICE_ID_FILE="/etc/deviceid"

if [ -f "${DEVICE_ID_FILE}" ]; then
    echo "File ${DEVICE_ID_FILE} already exists, skipping id generation!" >&2
    cat ${DEVICE_ID_FILE}
    exit 0
fi

# Try to use the network device as source for the device id:
NET_DEV=$(ls -lg /sys/class/net/ | grep -Fv -e virtual -e can | awk 'FNR==2 {print $8}')

if [ -z "${NET_DEV}" ]; then
    # Use machine-id as fallback:
    DEVICE_ID=$(md5sum /etc/machine-id | awk '{print $1}')
else
    DEVICE_ID=$(sed 's/\://g' /sys/class/net/"${NET_DEV}"/address)
fi

if [ -n "${DEVICE_ID}" ];
then
    echo "${DEVICE_ID}" > "${DEVICE_ID_FILE}"
    echo "${DEVICE_ID}"
    exit 0
else
    echo "Creating device id failed!" >&2
    exit 1
fi
