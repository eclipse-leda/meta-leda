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

SUMMARY = "SDV Core Utilities"
DESCRIPTION = "Core shell scripts"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/sdv-edge-core-utils;branch=main"
SRCREV = "0168b9efec3317328da1c5bdf454231268f6cb35"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
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
