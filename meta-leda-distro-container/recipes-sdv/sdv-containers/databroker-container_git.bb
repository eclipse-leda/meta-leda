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

SUMMARY = "DataBroker container image"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

require ../images/sdv-image-container.bb

IMAGE_INSTALL += " \
    busybox \
    databroker \
"

DATA_BROKER_PORT = "55555"
DATA_BROKER_METADATA = "/usr/share/vss/vss_rel_3.1.1.json"

OCI_IMAGE_TAG = "databroker:latest"
OCI_IMAGE_ENTRYPOINT = "/usr/bin/databroker"
OCI_IMAGE_PORTS = "${DATA_BROKER_PORT}"
OCI_IMAGE_ENV_VARS = " \
    KUKSA_DATA_BROKER_ADDR=0.0.0.0 \
    KUKSA_DATA_BROKER_PORT=${DATA_BROKER_PORT} \
    KUKSA_DATA_BROKER_METADATA_FILE=${DATA_BROKER_METADATA} \
"
