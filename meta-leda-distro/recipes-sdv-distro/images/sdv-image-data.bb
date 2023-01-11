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

SUMMARY = "An image containing files for the SDV data partition."
LICENSE = "Apache-2.0"


# TODO: Temporarily disabled AirGap container installations
# until projects have released containers
# IMAGE_INSTALL:append = " packagegroup-sdv-airgap"

DEPENDS += "sdv-default-containers"
IMAGE_INSTALL += "sdv-default-containers"
DEPENDS += "sdv-optional-containers"
IMAGE_INSTALL += "sdv-optional-containers"


IMAGE_FSTYPES += "ext4.gz"
IMAGE_LINGUAS = ""
IMAGE_ROOTFS_SIZE ?= "4194304"

IMAGE_PREPROCESS_COMMAND += "prepare_filesystem;"

inherit image

CONTAINER_DIR = "/var/containers"

prepare_filesystem() {
    install -d ${IMAGE_ROOTFS}/var/containerd/

    install -d ${IMAGE_ROOTFS}${CONTAINER_DIR}
    mv "${IMAGE_ROOTFS}${KANTO_MANIFESTS_DIR}" "${IMAGE_ROOTFS}${CONTAINER_DIR}"

    rm -rf ${IMAGE_ROOTFS}/etc/systemd
    rm -rf ${IMAGE_ROOTFS}/etc/ld.so.cache
    rm -rf ${IMAGE_ROOTFS}/run
    rm -rf ${IMAGE_ROOTFS}/var/cache
    rm -rf ${IMAGE_ROOTFS}/var/lib
    rm -rf ${IMAGE_ROOTFS}/data

}
