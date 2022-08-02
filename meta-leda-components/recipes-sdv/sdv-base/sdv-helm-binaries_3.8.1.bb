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

SUMMARY = "SDV: Helm3 Binaries"
DESCRIPTION = "Binary releases of helm"

inherit bin_package

# The sources contain the license file
SRC_URI = "https://github.com/helm/helm/archive/refs/tags/v3.8.1.zip;name=helm-sources"
SRC_URI[helm-sources.sha256sum] = "0f603de51381d0657dcc91fbd85f222566c99e3ca89e64331e19ed38d2e4761e"

# The DAPR CLI binaries
SRC_URI += "https://get.helm.sh/helm-v3.8.1-linux-arm64.tar.gz;name=helm-binaries-arm64;subdir=${WORKDIR}/helm-binaries/arm64"
SRC_URI[helm-binaries-arm64.sha256sum] = "dbf5118259717d86c57d379317402ed66016c642cc0d684f3505da6f194b760d"

SRC_URI += "https://get.helm.sh/helm-v3.8.1-linux-arm.tar.gz;name=helm-binaries-arm;subdir=${WORKDIR}/helm-binaries/arm"
SRC_URI[helm-binaries-arm.sha256sum] = "9f87153f545eea27b72ad9c639b197df792a4c784b403f118975e34a1bace2ca"

SRC_URI += "https://get.helm.sh/helm-v3.8.1-linux-amd64.tar.gz;name=helm-binaries-x86_64;subdir=${WORKDIR}/helm-binaries/x86_64"
SRC_URI[helm-binaries-x86_64.sha256sum] = "d643f48fe28eeb47ff68a1a7a26fc5142f348d02c8bc38d699674016716f61cd"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/helm-3.8.1/LICENSE;md5=6a18660411af65e17f370bdbb50e6957"


# Fix the already-stripped QA error
# ERROR: sdv-helm-binaries-3.8.1-r0 do_package: QA Issue: File '/usr/bin/helm' from sdv-helm-binaries was already stripped, this will prevent future debugging! [already-stripped]
INSANE_SKIP:${PN} += "already-stripped"

do_install () {
    install -d ${D}${bindir}
    case ${TARGET_ARCH} in
        aarch64*)    
            install -m 0755 ${WORKDIR}/helm-binaries/arm64/linux-arm64/helm ${D}${bindir}
            ;;
        arm*)
            install -m 0755 ${WORKDIR}/helm-binaries/arm/linux-arm/helm ${D}${bindir}
            ;;
        x86_64*)
            install -m 0755 ${WORKDIR}/helm-binaries/x86_64/linux-amd64/helm ${D}${bindir}
            ;;
        *)
            bbfatal "Recipe has no target-arch-to-helm-release-arch mapping for '${TARGET_ARCH}'."
            ;;
    esac

}