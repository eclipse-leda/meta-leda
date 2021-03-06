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

DESCRIPTION = "Eclipse Kanto - IoT Suite Connector"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

SRC_URI = "git://github.com/eclipse-kanto/suite-connector;protocol=https;branch=main"
SRCREV = "66267f96034267af6656d00073413e78a26c1c5f"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-kanto/suite-connector"

S = "${WORKDIR}/git"

inherit go-mod

do_compile() {
    cd ${B}/src/${GO_IMPORT}
    ${GO} build ${GOBUILDFLAGS} -o ${B}/ ./cmd/connector/
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/connector" "${D}/${bindir}"
}


