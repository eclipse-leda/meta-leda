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
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

SRC_URI = " \
  git://${GO_IMPORT}.git;protocol=https;branch=main \
  file://cloud-connector/ \
"
SRCREV = "6240d8ef1fc24339f2c97f4dde98cc463b24681d"
PV = "0.1+git${SRCPV}"

GO_IMPORT = "github.com/eclipse-leda/leda-contrib-cloud-connector"
PGM_NAME = "cloud-connector"

S = "${WORKDIR}/git"

do_compile[network] = "1"

inherit go-mod
inherit systemd

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "cloud-connector.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += " \
  ${bindir}/cloud-connector \
  ${systemd_system_unitdir}/cloud-connector.service \
  ${sysconfdir}/${PGM_NAME}/config.json \
  ${sysconfdir}/${PGM_NAME}/iothub.crt \
"

do_compile() {
  cd ${B}/src/${GO_IMPORT}
  ${GO} build ${GOBUILDFLAGS} -o ${B}/${PGM_NAME} ./cmd/
}

do_install() {
  install -d "${D}/${bindir}"
  install -m 0755 "${B}/${PGM_NAME}" "${D}/${bindir}"

  if [ "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}" ]; then
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/${PGM_NAME}/cloud-connector.service ${D}${systemd_system_unitdir}
  fi

  install -d "${D}/${sysconfdir}/${PGM_NAME}"
  install -m 0644 "${WORKDIR}/${PGM_NAME}/config.json" "${D}${sysconfdir}/${PGM_NAME}"
  install -m 0644 "${S}/src/${GO_IMPORT}/resources/iothub.crt" "${D}${sysconfdir}/${PGM_NAME}"
}
