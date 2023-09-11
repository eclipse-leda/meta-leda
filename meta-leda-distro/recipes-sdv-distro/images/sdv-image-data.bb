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

IMAGE_INSTALL += "sdv-core-containers"
IMAGE_INSTALL += "sdv-example-containers"
IMAGE_INSTALL += "sdv-example-certificate"
IMAGE_INSTALL += "sdv-example-blueprints"
IMAGE_INSTALL:append = " ${@bb.utils.contains("DISTRO_FEATURES", "airgap-containers", "packagegroup-sdv-airgap-containers", "", d)}"
IMAGE_FSTYPES += "ext4.gz"
IMAGE_LINGUAS = ""
IMAGE_ROOTFS_SIZE ?= "2097152"

IMAGE_PREPROCESS_COMMAND += "prepare_filesystem;"

inherit image

prepare_filesystem() {
    # Required by containerd
    install -d ${IMAGE_ROOTFS}/var/containerd/

    # Required by Self Update Agent (see sua.json)
    install -d ${IMAGE_ROOTFS}/selfupdates/
    
    # Where blueprints will be saved
    install -d "${IMAGE_ROOTFS}/var/containers/blueprints"

    rm -rf ${IMAGE_ROOTFS}/etc/systemd
    rm -rf ${IMAGE_ROOTFS}/etc/ld.so.cache
    rm -rf ${IMAGE_ROOTFS}/run
    rm -rf ${IMAGE_ROOTFS}/var/cache
    rm -rf ${IMAGE_ROOTFS}/var/lib
    rm -rf ${IMAGE_ROOTFS}/data

}

INCOMPATIBLE_LICENSE = "GPL-3.0* LGPL-3.0* AGPL-3.0*"
