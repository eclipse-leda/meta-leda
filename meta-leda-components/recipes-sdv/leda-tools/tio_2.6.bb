# /********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
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

SUMMARY = "tio - a simple serial device I/O tool"
DESCRIPTION = "tio is a simple serial device tool which features a \
    straightforward command-line and configuration file interface to easily \
    connect to serial TTY devices for basic I/O operations"

LICENSE = "GPL-2.0-or-later"

LIC_FILES_CHKSUM = "file://LICENSE;md5=0e1a95b7892d3015ecd6d0016f601f2c"

SRC_URI = "git://github.com/tio/tio;protocol=https;nobranch=1"

SRCREV = "88ef473362c5a1ff0dcd389fd5b1d4d6bf202472"
S = "${WORKDIR}/git"

inherit meson pkgconfig

DEPENDS += " libinih"
RDEPENDS:${PN} += " libinih"
FILES:${PN} += " /usr/share/bash-completion/completions/tio "