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
# eclipse-score-baselibs: Eclipse S-Core Base Libraries
#
# The baselibs module provides a collection of foundational C++ utility libraries
# for the S-CORE project, including:
#   - score/os:       OS abstraction layer (POSIX / QNX)
#   - score/result:   Error handling utilities (Result/Expected types)
#   - score/memory:   Memory management utilities (OffsetPtr, shared memory)
#   - score/language: Language utilities (safecpp, zstring_view, etc.)
#   - score/utils:    General utilities (ScopedOperation, etc.)
#   - score/mw/log:   Logging framework (logging middleware)
#
# The baselibs module is a core dependency of other S-CORE modules
# (communication, feo, etc.) and provides safe C++ primitives compliant
# with ASIL-B automotive safety standards.
#
# See: https://github.com/eclipse-score/baselibs
# See: https://eclipse-score.github.io/score/main/features/baselibs/index.html

SUMMARY = "Eclipse S-Core Base Libraries"
DESCRIPTION = "Foundational C++ utility libraries for the Eclipse S-CORE automotive \
middleware platform. Provides OS abstraction, memory management, error handling, \
logging, and safe C++ primitives for ASIL-B safety-critical systems."
HOMEPAGE = "https://github.com/eclipse-score/baselibs"
BUGTRACKER = "https://github.com/eclipse-score/baselibs/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/eclipse-score/baselibs.git;protocol=https;branch=main;nobranch=0"
SRCREV = "158fe6a7b791c58f6eac5f7e4662b8db0cf9ac6e"

PV = "0.2.4"
S = "${WORKDIR}/git"

inherit bazel

# Build all baselibs targets
BAZEL_TARGETS = "//score/..."

# Build with verbose failures and limit parallelism for Yocto builds
BAZEL_EXTRA_ARGS = " \
    --jobs=${@oe.utils.cpu_count()} \
    --sandbox_strategy=local \
    --verbose_failures \
"

# Output libraries produced by Bazel build.
# NOTE: Update after a successful build to match actual Bazel output paths.
BAZEL_OUTPUT_FILES = ""

DEPENDS += "bazel-native"

FILES:${PN} += "${libdir}/libscore_baselibs.so"
FILES:${PN}-dev += "${includedir}/score/"

INSANE_SKIP:${PN} += "dev-so"
FILES_SOLIBSDEV = ""
