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

SUMMARY = "SDV Core Utilities"
DESCRIPTION = "Core shell scripts"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/sdv-edge-core-utils;branch=main"
SRCREV = "cc8eadfd9421ac28c1847fa896a50e54c6ab33cb"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=2dd765ca47a05140be15ebafddbeadfe"

# Runtime Dependencies
RDEPENDS:${PN} += " bash"

# Force to refetch every time
#do_fetch[nostamp] = "1"

# Skip the compilation tasks
#do_compile[noexec] = "1"
#do_configure[noexec] = "1"
#do_package_qa[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/* ${D}${bindir}
}

FILES_${PN} += "${bindir}/${PN}/src/bash/ \
                ${bindir}/${PN}/LICENSE"

PACKAGES = "${PN}"
