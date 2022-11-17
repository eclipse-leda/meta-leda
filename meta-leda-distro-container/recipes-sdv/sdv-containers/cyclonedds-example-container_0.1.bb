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

SUMMARY = "SDV container images to demonstrate CycloneDDS"
DESCRIPTION ="The resulting image contains all the python examples from the CycloneDDS project"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

require ../images/sdv-image-container.bb

IMAGE_INSTALL += " \
    busybox \
    python3-cyclonedds \
    eclipse-cyclonedds-examples \
    "

OCI_IMAGE_TAG = "cyclonedds-example:${PV}"

IMAGE_CMD:append:oci() {
    cd ${IMGDEPLOYDIR}

    if [ -n "${OCI_IMAGE_TAR_OUTPUT}" ]; then

        cd $image_name
        tar -cf "../$image_name.tar" *
        cd -

	    ln -sf "$image_name.tar" "${IMAGE_BASENAME}-${MACHINE}-oci.tar"
    fi

    ln -sf $image_name ${IMAGE_BASENAME}-${MACHINE}-oci
}
