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
#
# This .bbappend modifies the crun recipe to compile a newer version, compatible with
# wasmedge and enables the --with-wasmedge compilation flag.
# This allows crun to run WASM/WASI based-containers as well as "normal" OCI containers.

# for compatibility for wasmedge, compile newer revisions for mickledore
SRCREV_crun = "d2ff390d37ca163ebff2467382b3a6d6c77c5ca6"
SRCREV_libocispec = "23aed835eed8d81d124977583551a81abe595a0c"
SRCREV_ispec = "4df8887994e871a59f9e30e8dd811d060f6a39ef"
SRCREV_rspec = "494a5a6aca782455c0fbfc35af8e12f04e98a55e"
SRCREV_yajl = "f344d21280c3e4094919fd318bc5ce75da91fc06"

# add wasmedge feature flag
EXTRA_OECONF += "--with-wasmedge"
DEPENDS += "wasmedge"
