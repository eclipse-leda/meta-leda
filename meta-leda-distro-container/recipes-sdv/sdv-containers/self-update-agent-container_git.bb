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

SUMMARY = "Self Update Agent container image"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

require ../images/sdv-image-container.bb

IMAGE_INSTALL += " \
    busybox \
    leda-contrib-self-update-agent \
    leda-contrib-self-update-agent-data \
    "

OCI_IMAGE_TAG = "self-update-agent:latest"
OCI_IMAGE_ENTRYPOINT = "/usr/bin/self-update-agent"
OCI_IMAGE_ENTRYPOINT_ARGS = "-p /data/selfupdates --host mosquitto:1883"
