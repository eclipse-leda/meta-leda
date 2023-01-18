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

SUMMARY = "Self Update Agent offers remote OS updates for edge devices using RAUC"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a832eda17114b48ae16cda6a500941c2"

SRC_URI = "gitsm://github.com/eclipse-leda/leda-contrib-self-update-agent.git;protocol=https;branch=main"
SRCREV = "f37df38cde19ef7974a8ceeb2b4f4dbbf288f43f"

SRC_URI += " \
    file://self-update-agent/self-update-agent.service \
    file://self-update-agent/0001-CMakeLists-root.patch \
    file://self-update-agent/0002-CMakeLists-3rdparty.patch \
    file://self-update-agent/0003-CMakeLists-src.patch \
"

S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

EXTRA_OECMAKE += "-DSUA_COMMIT_HASH=${SRCREV} -DSUA_BUILD_NUMBER=${PR}"

DEPENDS += "curl"
DEPENDS += "dbus-glib"
DEPENDS += "glib-networking"
DEPENDS += "nlohmann-json"
DEPENDS += "paho-mqtt-cpp"

PACKAGES = "${PN}-data ${PN} ${PN}-dbg"

SYSTEMD_SERVICE:${PN} = "self-update-agent.service"

FILES:${PN} += " \
    ${systemd_system_unitdir}/self-update-agent.service \
    ${bindir}/self-update-agent \
    ${libdir}/libmini-yaml.so \
"

FILES:${PN}-data += " \
    /data/selfupdates \
"

FILES:${PN}-dbg += " \
    ${bindir}/.debug/ \
"

do_install() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/self-update-agent/self-update-agent.service ${D}${systemd_unitdir}/system

    install -d ${D}${bindir}
    install -m 755 ${B}/src/self-update-agent ${D}${bindir}

    install -d ${D}${libdir}
    install -m 755 ${B}/3rdparty/libmini-yaml.so ${D}${libdir}

    install -d ${D}/data/selfupdates
}
