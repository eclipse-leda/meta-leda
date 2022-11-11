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
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/github.com/eclipse-leda/leda-incubator-cloudagent/NOTICE;md5=b95389a3f134a33b445b438d337848f7"

SRC_URI =  " \
    git://github.com/eclipse-leda/leda-contrib-cloud-connector;branch=main \
    file://cloudagent-systemd/ \
"
SRCREV = "86aabb2712157414398d786d323bed1ff752b666"

GO_IMPORT = "github.com/eclipse-leda/leda-incubator-cloudagent"

S = "${WORKDIR}/git"

inherit go-mod
inherit systemd features_check

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "cloudagent.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "${bindir}/cloudagent \
                ${systemd_system_unitdir}/cloudagent.service"

REQUIRED_DISTRO_FEATURES = "systemd"

do_compile() {
  cd ${B}/src/${GO_IMPORT}
  ${GO} build ${GOBUILDFLAGS} -o ${B}/cloudagent ./cmd/
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/cloudagent" "${D}/${bindir}"

  install -d ${D}${systemd_unitdir}/system/
  install -m 0644 ${WORKDIR}/cloudagent-systemd/cloudagent.service ${D}${systemd_system_unitdir}
}
