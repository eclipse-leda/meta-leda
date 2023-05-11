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

SUMMARY = "Seat Service Example container image"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

require ../images/sdv-image-container.bb

IMAGE_INSTALL += " \
    busybox \
    coreutils-stdbuf \
    seat-service-example \
"

OCI_IMAGE_TAG = "seat-service-example:latest"
OCI_IMAGE_ENTRYPOINT = "/usr/bin/val_start.sh"
OCI_IMAGE_ENV_VARS = " \
    CAN=cansim \
    SERVICE_HOST=0.0.0.0 \
    SERVICE_PORT=50051 \
    BROKER_ADDR=databroker:55555 \
"
