# /********************************************************************************
# * Copyright (c) 2026 Contributors to the Eclipse Foundation
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

DESCRIPTION = "WasmEdge is a lightweight, high-performance, and extensible WebAssembly runtime."

inherit cmake

SRC_URI = "git://github.com/WasmEdge/WasmEdge.git;protocol=https;nobranch=1"
SRCREV = "1ebe13e9a0483892dd97c77747a3ef57fead3c22"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

DEPENDS += " boost spdlog clang lld"

EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Release"
