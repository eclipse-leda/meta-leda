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

DESCRIPTION = "Eclipse Kanto - Software Update"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

SRC_URI = "git://github.com/eclipse-kanto/software-update;protocol=https;branch=main"
SRCREV = "158cf1ccf69232978d04fb370031a25513060d9c"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-kanto/software-update"

S = "${WORKDIR}/git"

inherit go-mod

do_compile[network] = "1"

do_compile() {
    cd ${B}/src/${GO_IMPORT}
    ${GO} build ${GOBUILDFLAGS} -o ${B}/ ./cmd/software-update/
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/software-update" "${D}/${bindir}"
}


