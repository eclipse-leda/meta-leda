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

SUMMARY = "GraphQL implementation for Python, a port of GraphQL.js, the JavaScript reference implementation for GraphQL"
HOMEPAGE = "https://github.com/graphql-python/graphql-core"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=73706fb002de2debc52328afd1688817"

SRC_URI[sha256sum] = "06d2aad0ac723e35b1cb47885d3e5c45e956a53bc1b209a9fc5369007fe46676"

PYPI_PACKAGE = "graphql-core"

inherit pypi setuptools3

BBCLASSEXTEND = "native"
