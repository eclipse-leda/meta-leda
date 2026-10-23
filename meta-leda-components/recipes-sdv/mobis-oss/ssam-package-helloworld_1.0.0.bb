# /********************************************************************************
# * Copyright (c) 2026 Contributors to the Eclipse Foundation
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
# Example SSAM package: a static hello-world binary in an otherwise empty package
# root. See classes/ssam-package.bbclass for the reusable part.

SUMMARY = "SSAM package example (helloworld)"
DESCRIPTION = "Example SSAM package built at build time with ssam-wrap. \
The package root contains a static hello-world binary; ssamd deploys it as a \
container when the package is started."
HOMEPAGE = "https://github.com/mobis-oss/ssam"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${UNPACKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit ssam-package

SRC_URI += "file://LICENSE"
SRC_URI += "file://private-key.pem"

PV = "1.0.0"

SSAM_PKG_NAME = "helloworld"

# Run the binary from the package root instead of the generated default.
SSAM_PKG_ARGS = "/${SSAM_PKG_NAME}"

do_prepare_pkgfs() {
    # The package root is only the pkgfs tree, so the entry point must not need
    # shared libraries. Strip it: ssam-wrap sizes the pkgfs image from the source
    # directory with a small overhead only.
    printf '#include <stdio.h>\nint main(void)\n{\n        puts("Hello from SSAM!");\n        return 0;\n}\n' > ${WORKDIR}/hello.c
    ${CC} -static -o ${WORKDIR}/hello ${WORKDIR}/hello.c
    install -s -m 0755 ${WORKDIR}/hello ${SSAM_PKG_WS}/pkgfs/${SSAM_PKG_NAME}
}
