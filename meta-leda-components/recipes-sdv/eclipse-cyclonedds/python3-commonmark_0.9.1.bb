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

SUMMARY = "Python parser for the CommonMark Markdown spec"
HOMEPAGE = "https://github.com/rtfd/commonmark.py"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=37e127eb75a030780aefcfc584e78523"

SRC_URI[sha256sum] = "452f9dc859be7f06631ddcb328b6919c67984aca654e5fefb3914d54691aed60"

inherit pypi setuptools3

