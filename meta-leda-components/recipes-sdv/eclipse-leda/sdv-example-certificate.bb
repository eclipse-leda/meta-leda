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
#

DESCRIPTION = "Example Device Certificate"
SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# Default example filenames (source filenames)
CLOUDCONNECTOR_EXAMPLE_CERTFILE = "example-device.crt"
CLOUDCONNECTOR_EXAMPLE_KEYFILE = "example-device.key"

# Target filenames are hardcoded to device.crt and device.key, as they are statically configured in the container deployment manifest
CLOUDCONNECTOR_CERTIFICATE_CERTFILE ??= ""
CLOUDCONNECTOR_CERTIFICATE_CERTFILE[doc] = "Specify the source filename of the initial example device certificate file"
CLOUDCONNECTOR_CERTIFICATE_KEYFILE ??= ""
CLOUDCONNECTOR_CERTIFICATE_KEYFILE[doc] = "Specify the source filename of the initial example device certificate private key"

SRC_URI += "file://cloud-connector/${CLOUDCONNECTOR_EXAMPLE_CERTFILE}"
SRC_URI += "file://cloud-connector/${CLOUDCONNECTOR_EXAMPLE_KEYFILE}"

S = "${WORKDIR}"

# Runtime configuration in /var/containers/manifests/cloudconnector.json
CLOUDCONNECTOR_CERTIFICATE_DIR ?= "/var/certificates"

do_install:append() {
    install -d ${D}${CLOUDCONNECTOR_CERTIFICATE_DIR}

	if [ -z ${CLOUDCONNECTOR_CERTIFICATE_CERTFILE} ]; then
		bbwarn "'CLOUDCONNECTOR_CERTIFICATE_CERTFILE' is not set, falling back to ${CLOUDCONNECTOR_EXAMPLE_CERTFILE}. Please create your own certificate and override CLOUDCONNECTOR_CERTIFICATE_CERTFILE in local.conf"
        install ${S}/cloud-connector/${CLOUDCONNECTOR_EXAMPLE_CERTFILE} ${D}${CLOUDCONNECTOR_CERTIFICATE_DIR}/device.crt
    else
        install ${CLOUDCONNECTOR_CERTIFICATE_CERTFILE} ${D}${CLOUDCONNECTOR_CERTIFICATE_DIR}/device.crt
	fi

	if [ -z ${CLOUDCONNECTOR_CERTIFICATE_KEYFILE} ]; then
		bbwarn "'CLOUDCONNECTOR_CERTIFICATE_KEYFILE' is not set, falling back to ${CLOUDCONNECTOR_EXAMPLE_KEYFILE}. Please create your own certificate and override CLOUDCONNECTOR_CERTIFICATE_KEYFILE in local.conf"
        install ${S}/cloud-connector/${CLOUDCONNECTOR_EXAMPLE_KEYFILE} ${D}${CLOUDCONNECTOR_CERTIFICATE_DIR}/device.key
    else
        install ${CLOUDCONNECTOR_CERTIFICATE_KEYFILE} ${D}${CLOUDCONNECTOR_CERTIFICATE_DIR}/device.key
	fi

}

PACKAGES = "${PN}"
FILES:${PN} += "${CLOUDCONNECTOR_CERTIFICATE_DIR}/device.crt"
FILES:${PN} += "${CLOUDCONNECTOR_CERTIFICATE_DIR}/device.key"
