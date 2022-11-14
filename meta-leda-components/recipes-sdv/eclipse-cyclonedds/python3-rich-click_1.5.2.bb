# ********************************************************************************
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
# ********************************************************************************

SUMMARY = "Format click help output nicely with rich"
HOMEPAGE = "https://github.com/ewels/rich-click"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5372b77c3720be60b7eff9a9a5c0000d"

SRC_URI[sha256sum] = "a57ca70242cb8b372a670eaa0b0be48f2440b66656deb4a56e6aadc1bbb79670"

inherit pypi setuptools3

RDEPENDS:${PN} += " python3-core python3-click python3-rich"

