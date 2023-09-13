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

SUMMARY = "Eclipse Kuksa - GPS Feeder container image"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

require ../images/sdv-image-container.bb

IMAGE_INSTALL += " \
    busybox \
    gpsd \
    kuksa-gps-feeder \
"

OCI_IMAGE_TAG = "kuksa-gps-feeder:latest"
OCI_IMAGE_ENTRYPOINT = "entrypoint_gps_feeder.sh"
OCI_IMAGE_ENTRYPOINT_ARGS = "--insecure=True --ip databroker --port 55555 --protocol grpc"
OCI_IMAGE_ENV_VARS = 'GPSD_OPTIONS="-S 2948 gpsd://host:2947"'
