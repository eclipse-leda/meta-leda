# ********************************************************************************
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
# ********************************************************************************

SUMMARY = "A simple gpsd client."
HOMEPAGE = "https://github.com/tfeldmann/gpsdclient"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=899c824b3a6926844787ad42a3e5df56"

SRC_URI[sha256sum] = "70a496550a9747dff5e0e50b3c95a6e1dcab9d842860997e95120767e2060a7a"

inherit pypi setuptools3
