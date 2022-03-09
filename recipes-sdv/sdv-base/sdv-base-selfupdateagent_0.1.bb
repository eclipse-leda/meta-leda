# /********************************************************************************
# * Copyright (c) 2021 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0
# *
# * SPDX-License-Identifier: EPL-2.0
# ********************************************************************************/

SUMMARY = "SDV Core Self Update Agent"
DESCRIPTION = "Bridge to RAUC Self Updater"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/sdv-edge-core-utils;branch=main"
SRCREV = "5bc1f0c565c89ddea577ebe20df73a1a88557d5d"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=2dd765ca47a05140be15ebafddbeadfe"
 
# Runtime Dependencies
RDEPENDS:${PN} += "bash"

do_install() {
    install -d ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/* ${D}${bindir}
}

FILES_${PN} += "${datadir}/${PN}/src/bash/ \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"