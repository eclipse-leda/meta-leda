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
# bazel-native: Provides the Bazel build tool as a native (host) tool.
#
# Eclipse S-Core modules require Bazel 8.x (Bzlmod / MODULE.bazel support).
# This recipe downloads the self-contained Bazel binary and installs it into
# the native sysroot so that bazel.bbclass can reference it.
#
# Bazel 8.x is a JDK-free, self-contained binary. No Java installation is
# needed on the build host. The binary is architecture-specific (x86_64).

SUMMARY = "Bazel build tool - native"
DESCRIPTION = "Bazel is a fast, scalable, multi-language, extensible build system. \
This recipe provides Bazel as a native (host) tool for use in cross-compilation \
recipes that depend on the Bazel build system, such as Eclipse S-Core components."
HOMEPAGE = "https://bazel.build"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit native

PV = "8.6.0"

BAZEL_ARCH = "x86_64"

SRC_URI = "https://github.com/bazelbuild/bazel/releases/download/${PV}/bazel-${PV}-linux-${BAZEL_ARCH};downloadfilename=bazel \
           https://raw.githubusercontent.com/bazelbuild/bazel/${PV}/LICENSE;name=license"

SRC_URI[sha256sum] = "9860da9c9386bbc023feed8f43af3105d338727d77b644fa6aeca45a4a11957c"
SRC_URI[license.sha256sum] = "cfc7749b96f63bd31c3c42b5c471bf756814053e847c10f3eb003417bc523d30"

# No configure or compile steps needed for a pre-built binary
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d "${D}${bindir}"
    install -m 755 "${WORKDIR}/bazel" "${D}${bindir}/bazel"
    install -d "${D}${datadir}/bazel"
    install -m 644 "${WORKDIR}/LICENSE" "${D}${datadir}/bazel/LICENSE"
}

FILES:${PN} += "${datadir}/bazel"

# Skip standard QA checks that are irrelevant for pre-built native binaries
INSANE_SKIP:${PN} += "already-stripped staticdev ldflags file-rdeps"
