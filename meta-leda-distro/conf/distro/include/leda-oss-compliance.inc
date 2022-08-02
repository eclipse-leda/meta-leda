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

# Package source tarballs to comply with OSS licenses
# Documentation: https://docs.yoctoproject.org/dev-manual/common-tasks.html#providing-the-source-code
# Sources are then in tmp/deploy/sources
INHERIT += "archiver"
ARCHIVER_MODE[src] = "patched"

COPYLEFT_LICENSE_INCLUDE = 'GPL* LGPL*'
COPYLEFT_LICENSE_EXCLUDE = 'CLOSED Proprietary MIT BSD'
COPYLEFT_RECIPE_TYPES = "target"
ARCHIVER_MODE[mirror] = "combined"

# Some licenses require the license text to accompany the binary. You can achieve this by adding the following:
COPY_LIC_MANIFEST = "1"
COPY_LIC_DIRS = "1"
LICENSE_CREATE_PACKAGE = "1"

# Vulnerability Scanning
INHERIT += "cve-check"