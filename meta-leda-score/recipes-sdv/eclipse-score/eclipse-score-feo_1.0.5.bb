# ********************************************************************************
# * Copyright (c) 2024 Contributors to the Eclipse Foundation
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
#
# eclipse-score-feo: Eclipse S-Core Fixed Order Execution (FEO) Framework
#
# The FEO (Fixed Order Execution) framework is the execution runtime for
# S-CORE platform applications. It provides:
#
#   - Deterministic scheduling: Fixed-order execution of task components
#   - Process supervision: Component lifecycle management
#   - IPC integration: Built-in integration with LoLa communication
#   - Multi-agent support: Distributed execution across multiple processes
#   - Error propagation: Structured error handling and shutdown
#
# FEO is implemented in Rust and uses Bazel as its build system. It provides
# binary executables that serve as the execution framework for S-CORE
# applications. Applications implement FEO component interfaces and are
# loaded as dynamic libraries or separate processes.
#
# Note: This module depends on the Rust toolchain via the Bazel build system.
# The build uses Bazel's rules_rust to compile Rust code and rules_rust_prost
# for protobuf-based IPC.
#
# See: https://github.com/eclipse-score/feo
# See: https://eclipse-score.github.io/score/main/features/feo/index.html

SUMMARY = "Eclipse S-Core Fixed Order Execution (FEO) Framework"
DESCRIPTION = "Deterministic execution runtime for Eclipse S-CORE automotive middleware. \
Provides fixed-order task scheduling, process supervision, and lifecycle management \
for Software-Defined Vehicle applications."
HOMEPAGE = "https://github.com/eclipse-score/feo"
BUGTRACKER = "https://github.com/eclipse-score/feo/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/eclipse-score/feo.git;protocol=https;branch=main;nobranch=0"
SRCREV = "313b3d849c17881d3a11b6bf62aa2afab6565ef0"

PV = "1.0.5"
S = "${WORKDIR}/git"

inherit bazel

# Build the FEO supervisor and agent binaries
BAZEL_TARGETS = "//src/..."

# Build with verbose failures and limit parallelism for Yocto builds
BAZEL_EXTRA_ARGS = " \
    --jobs=${@oe.utils.cpu_count()} \
    --sandbox_strategy=local \
    --verbose_failures \
"

# Output binaries produced by the Bazel build.
# NOTE: Update after a successful build to match actual Bazel output paths.
BAZEL_OUTPUT_FILES = " \
    src/score_feo_supervisor \
    src/score_feo_agent \
"

DEPENDS += "bazel-native"

do_install:append() {
    install -d "${D}${sysconfdir}/score/feo"
}

FILES:${PN} += " \
    ${bindir}/score_feo_supervisor \
    ${bindir}/score_feo_agent \
    ${sysconfdir}/score/feo \
"
