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

SUMMARY = "SDV: DAPR Binaries"
DESCRIPTION = "Binary releases of the dapr.io runtime"

#SRC_URI = "https://github.com/dapr/cli/releases/download/v1.6.0/dapr_linux_arm64.tar.gz"
#SRC_URI = "https://github.com/dapr/cli/releases/download/v1.6.0/dapr_linux_amd64.tar.gz"

inherit bin_package

SRC_URI = "https://github.com/dapr/cli/releases/download/v1.6.0/dapr_linux_amd64.tar.gz;name=dapr-binaries;subdir=${BP} \
    https://github.com/dapr/cli/archive/refs/tags/v1.6.0.tar.gz;name=dapr-sources"

SRC_URI[dapr-binaries.sha256sum] = "a1b4dbb3a8bf9746951f5c9889d8ddb08f28f931c10c396b82ce4362eea92198"
SRC_URI[dapr-sources.sha256sum] = "2dfe446f472448b33cf3feb2293d8bb896f50c82a110b2e69b09b759559995ab"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/cli-1.6.0/LICENSE;md5=04c18f3bbbc4178e5056fbfd678c8d9d"
