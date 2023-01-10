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

SRC_URI = "gitsm://github.com/SoftwareDefinedVehicle/leda-contrib-self-update-agent-fork.git;protocol=https;branch=main"
SRCREV = "db7ad3935bf2f1fae8207044e15cc78fc6d2824e"
SRC_URI += "file://self-update-agent/"


# SRC_URI += "file://self-update-agent/self-update-agent.service"
# SRC_URI += "file://self-update-agent/CMakeLists-3rdparty.txt"
# SRC_URI += "file://self-update-agent/CMakeLists-src.txt"
# SRC_URI += "file://self-update-agent/CMakeLists-root.txt"


S = "${WORKDIR}/git"

inherit cmake pkgconfig systemd

EXTRA_OECMAKE += "-DSUA_COMMIT_HASH=${SRCREV} -DSUA_BUILD_NUMBER=${PR}"

DEPENDS += "curl"
DEPENDS += "dbus-glib"
DEPENDS += "glib-networking"
DEPENDS += "nlohmann-json"
DEPENDS += "paho-mqtt-cpp"


# DEPENDS += "spdlog"


PACKAGES = "${PN}-data ${PN}"

SYSTEMD_SERVICE:${PN} = "self-update-agent.service"

FILES:${PN} += " \
    ${systemd_system_unitdir}/self-update-agent.service \
    ${bindir}/self-update-agent \
    ${libdir}/libmini-yaml.so \
"

FILES:${PN}-data += " \
    /data/self-update-images/ \
"

# TODO dev temp
do_compile:prepend() {
    cp ${B}/../self-update-agent/CMakeLists-3rdparty.txt ${S}/3rdparty/CMakeLists.txt
    cp ${B}/../self-update-agent/CMakeLists-src.txt ${S}/src/CMakeLists.txt
    cp ${B}/../self-update-agent/CMakeLists-root.txt ${S}/CMakeLists.txt
}


do_install() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/self-update-agent/self-update-agent.service ${D}${systemd_unitdir}/system

    install -d ${D}${bindir}
    install -m 755 ${B}/src/self-update-agent ${D}${bindir}

    install -d ${D}${libdir}
    install -m 755 ${B}/3rdparty/libmini-yaml.so ${D}${libdir}

    install -d ${D}/data/self-update-images/
}
