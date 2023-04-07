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

SRCREV_FORMAT = "sua_pahoc_pahoccp_yaml_curl_googletest_glib_spdlog_openssl_json"

SRC_URI += "git://github.com/eclipse-leda/leda-contrib-self-update-agent.git;name=sua;protocol=https;branch=main \
            git://github.com/eclipse/paho.mqtt.c.git;protocol=https;name=pahoc;branch=master;destsuffix=git/3rdparty/paho.mqtt.c \
            git://github.com/eclipse/paho.mqtt.cpp.git;protocol=https;name=pahocpp;branch=master;destsuffix=git/3rdparty/paho.mqtt.cpp \
            git://github.com/jimmiebergmann/mini-yaml.git;protocol=https;name=yaml;branch=master;destsuffix=git/3rdparty/mini-yaml \
            git://github.com/curl/curl.git;protocol=https;name=curl;branch=master;destsuffix=git/3rdparty/curl \
            git://github.com/google/googletest.git;protocol=https;name=googletest;branch=main;destsuffix=git/3rdparty/googletest \
            git://github.com/GNOME/glib.git;protocol=https;name=glib;branch=main;destsuffix=git/3rdparty/glib \
            git://github.com/gabime/spdlog.git;protocol=https;name=spdlog;branch=v1.x;destsuffix=git/3rdparty/spdlog \
            git://github.com/openssl/openssl.git;protocol=https;name=openssl;branch=master;destsuffix=git/3rdparty/openssl \
            git://github.com/nlohmann/json.git;protocol=https;name=json;branch=develop;destsuffix=git/3rdparty/nlohmann-json \
            "
SRCREV_sua = "f37df38cde19ef7974a8ceeb2b4f4dbbf288f43f"
SRCREV_pahoc = "556cd568345e47b70da603edc92f11ff94a6161f"
SRCREV_pahocpp = "2ff3d155dcd10564f1816675789284b4efd79eb7"
SRCREV_yaml = "22d3dcf5684a11f9c0508c1ad8b3282a1d888319"
SRCREV_curl = "a8e02881ec9417706610443bcfee6e1104bb44c6"
SRCREV_googletest = "9c332145b71c36a5bad9688312c79184f98601ff"
SRCREV_glib = "da2702646c1065eaa2d501a7f33776dcc2f0f11c"
SRCREV_spdlog = "4f800773393d3ebac13c1fcd946a315d4d72bcd9"
SRCREV_openssl = "a275afc527d05b5187b457bdbcd0e1dcb18839f1"
SRCREV_json = "7f72eedc2d4fc196d389f5aa0b2659f70dabe278"

SRC_URI += " \
    file://self-update-agent/self-update-agent.service \
    file://self-update-agent/0001-CMakeLists-root.patch \
    file://self-update-agent/0002-CMakeLists-3rdparty.patch \
    file://self-update-agent/0003-CMakeLists-src.patch \
"

S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

EXTRA_OECMAKE += "-DSUA_COMMIT_HASH=${SRCREV_sua} -DSUA_BUILD_NUMBER=${PR}"

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
