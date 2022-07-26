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

SUMMARY = "SDV: DAPR Binaries"
DESCRIPTION = "Binary releases of the dapr.io runtime"

inherit bin_package

# The sources contain the license file
SRC_URI = "https://github.com/dapr/cli/archive/refs/tags/v1.7.1.tar.gz;name=dapr-sources"
SRC_URI[dapr-sources.sha256sum] = "41e14d8e1d711e062db328be96c6e196478137bfc1ac0b029c2e4d3b9b682673"
                                
# The DAPR CLI binaries
SRC_URI += "https://github.com/dapr/cli/releases/download/v1.7.1/dapr_linux_arm64.tar.gz;name=dapr-binaries-arm64;subdir=${WORKDIR}/dapr-binaries/arm64"
SRC_URI[dapr-binaries-arm64.sha256sum] = "e441f27e9870c61d7a078fecdb9d54836d25915245e4c4c62ed7858324f544c1"

SRC_URI += "https://github.com/dapr/cli/releases/download/v1.7.1/dapr_linux_arm.tar.gz;name=dapr-binaries-arm;subdir=${WORKDIR}/dapr-binaries/arm"
SRC_URI[dapr-binaries-arm.sha256sum] = "c5b2e6457013bc945f75810578f8ea95a5721d38d43350d972aea887b3f38e1a"

SRC_URI += "https://github.com/dapr/cli/releases/download/v1.7.1/dapr_linux_amd64.tar.gz;name=dapr-binaries-x86_64;subdir=${WORKDIR}/dapr-binaries/x86_64"
SRC_URI[dapr-binaries-x86_64.sha256sum] = "896f1d7b1c244f06311354162b53b048885152df93c1a94abf06c76ac6837de6"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/cli-1.7.1/LICENSE;md5=04c18f3bbbc4178e5056fbfd678c8d9d"

do_install () {
    install -d ${D}${bindir}
    case ${TARGET_ARCH} in
        aarch64*)    
            install -m 0755 ${WORKDIR}/dapr-binaries/arm64/dapr ${D}${bindir}
            ;;
        arm*)
            install -m 0755 ${WORKDIR}/dapr-binaries/arm/dapr ${D}${bindir}
            ;;
        x86_64*)
            install -m 0755 ${WORKDIR}/dapr-binaries/x86_64/dapr ${D}${bindir}
            ;;
        *)
            bbfatal "Recipe has no target-arch-to-dapr-release-arch mapping for '${TARGET_ARCH}'."
            ;;
    esac

}