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

DESCRIPTION = "Setup individual device configuration"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

SRC_URI += "file://sdv-device-config/"

FILES:${PN} += " \
    ${sbindir}/sdv-generate-deviceid.sh \
    ${sbindir}/sdv-set-hostname.sh \
"

do_install() {
    install -d ${D}${sbindir}
    install -m 755 ${WORKDIR}/sdv-device-config/sdv-generate-deviceid.sh ${D}${sbindir}/
    install -m 755 ${WORKDIR}/sdv-device-config/sdv-set-hostname.sh ${D}${sbindir}/
}

pkg_postinst_ontarget:${PN} () {
    ${sbindir}/sdv-set-hostname.sh
}
