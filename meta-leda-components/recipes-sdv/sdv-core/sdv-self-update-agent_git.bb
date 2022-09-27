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

# Dependencies of Self Update Agent
DEPENDS += "glib-2.0"
DEPENDS += "glib-networking"
DEPENDS += "openssl"
DEPENDS += "curl"
DEPENDS += "paho-mqtt-c"
DEPENDS += "paho-mqtt-cpp"

DEPENDS += "dbus"
DEPENDS += "dbus-glib"

# Use the gitsm fetcher to initialize submodules
SRC_URI = "gitsm://github.com/SoftwareDefinedVehicle/sdv-self-update-agent.git;branch=main;protocol=https"
SRC_URI += "file://self-update-agent/self-update-agent.service"
SRC_URI += "file://self-update-agent/CMakeLists-3rdparty.txt"
SRC_URI += "file://self-update-agent/CMakeLists-src.txt"
SRC_URI += "file://self-update-agent/CMakeLists-root.txt"
SRCREV  = "6715892eabeb5d4ee72929361e5dbddeeeeba661"

S = "${WORKDIR}/git"

OECMAKE_GENERATOR = "Unix Makefiles"

inherit cmake gio-module-cache gobject-introspection pkgconfig

PACKAGES = "${PN}"

FILES:${PN} += "${systemd_unitdir}/system/self-update-agent.service"
FILES:${PN} += "${bindir}/sdv-self-update-agent"
FILES:${PN} += "${libdir}/libmini-yaml.so"

do_compile:prepend() {
    # Hacky overwrite
    cp ${B}/../self-update-agent/CMakeLists-3rdparty.txt ${S}/3rdparty/CMakeLists.txt
    cp ${B}/../self-update-agent/CMakeLists-src.txt ${S}/src/CMakeLists.txt
    cp ${B}/../self-update-agent/CMakeLists-root.txt ${S}/CMakeLists.txt
}

do_install() {
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/self-update-agent/self-update-agent.service ${D}${systemd_unitdir}/system

    install -d ${D}${bindir}
    install -m 755 ${B}/src/sdv-self-update-agent ${D}${bindir}

    install -d ${D}${libdir}
    install -m 755 ${B}/3rdparty/libmini-yaml.so ${D}${libdir}
}
