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
SRC_URI = "https://github.com/dapr/cli/archive/refs/tags/v1.6.0.tar.gz;name=dapr-sources"
SRC_URI[dapr-sources.sha256sum] = "2dfe446f472448b33cf3feb2293d8bb896f50c82a110b2e69b09b759559995ab"

# The DAPR CLI binaries
SRC_URI += "https://github.com/dapr/cli/releases/download/v1.6.0/dapr_linux_arm64.tar.gz;name=dapr-binaries-arm64;subdir=${WORKDIR}/dapr-binaries/arm64"
SRC_URI[dapr-binaries-arm64.sha256sum] = "f54c4d068957e9c3ba12548a914f9b5ea1906cd4a5b5ecf90d3b15077ec558a3"

SRC_URI += "https://github.com/dapr/cli/releases/download/v1.6.0/dapr_linux_arm.tar.gz;name=dapr-binaries-arm;subdir=${WORKDIR}/dapr-binaries/arm"
SRC_URI[dapr-binaries-arm.sha256sum] = "ef88f7670bcb7486a6f57d81167c3f6611c9310419f5405faa999460cedc341c"

SRC_URI += "https://github.com/dapr/cli/releases/download/v1.6.0/dapr_linux_amd64.tar.gz;name=dapr-binaries-x86_64;subdir=${WORKDIR}/dapr-binaries/x86_64"
SRC_URI[dapr-binaries-x86_64.sha256sum] = "a1b4dbb3a8bf9746951f5c9889d8ddb08f28f931c10c396b82ce4362eea92198"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/cli-1.6.0/LICENSE;md5=04c18f3bbbc4178e5056fbfd678c8d9d"

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