# /********************************************************************************
# * Copyright (c) 2023-2026 Contributors to the Eclipse Foundation
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
#
# This .bbappend enables WasmEdge support in the crun version provided by
# meta-virtualization, retaining its matching source revisions and CVE patches.
# This allows crun to run WASM/WASI based-containers as well as "normal" OCI containers.

# add wasmedge feature flag
EXTRA_OECONF += "--with-wasmedge"
DEPENDS += "wasmedge"
