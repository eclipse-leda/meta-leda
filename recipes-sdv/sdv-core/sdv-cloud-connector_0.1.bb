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

SUMMARY = "SDV Cloud Connector"
DESCRIPTION = "Customized fork of Eclipse Kanto azure-connector with additional support of Eclipse Backend Function Bindings and Azure C2D messages"
LICENSE = "Apache-2.0"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/swdc-os-cloud-agent.git;branch=main"
SRCREV = "86aabb2712157414398d786d323bed1ff752b666"

# Replace 'xxx' after first build with correct value
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/NOTICE;md5=b95389a3f134a33b445b438d337848f7"

#GO_IMPORT = "import"
#GO_INSTALL = "${GO_IMPORT}/hello"
#GO_WORKDIR = "${GO_INSTALL}"
#export GO111MODULE="off"

DEPENDS = "go-cross-${TARGET_ARCH}"
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
S = "${WORKDIR}"

inherit go
inherit goarch

do_compile() {
  go build
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${S}/helloworld" "${D}/${bindir}"
}
