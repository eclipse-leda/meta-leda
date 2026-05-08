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
# eclipse-score-communication: Eclipse S-Core Communication Module (LoLa)
#
# The Communication Module (LoLa - Low Latency) provides a high-performance,
# safety-critical inter-process communication (IPC) middleware based on the
# Adaptive AUTOSAR Communication Management specification.
#
# Key features:
#  - Zero-copy, shared-memory based IPC for minimal latency within ECUs
#  - AUTOSAR ara::com compliant API (partial implementation)
#  - Publisher/subscriber pattern with skeleton/proxy framework
#  - Service discovery via flag-file mechanism
#  - ASIL-B safety-critical design
#  - Multi-platform: Linux and QNX support
#
# The module is built using Bazel (Bzlmod / MODULE.bazel). The bazel.bbclass
# handles invoking Bazel with the host toolchain. Cross-compilation for the
# Yocto target machine requires a matching Bazel platform configuration.
#
# Note: Bazel fetches external dependencies (Bzlmod modules) at build time.
# Network access is required during the first build unless a pre-seeded Bazel
# module cache is available. Set BB_NO_NETWORK = "0" to allow network access
# during the build, or pre-populate BAZEL_REPOSITORY_CACHE.
#
# See: https://github.com/eclipse-score/communication
# See: https://eclipse-score.github.io/score/main/features/communication/index.html

SUMMARY = "Eclipse S-Core Communication Module (LoLa) - IPC middleware"
DESCRIPTION = "High-performance, safety-critical IPC middleware based on Adaptive \
AUTOSAR Communication Management specification. Provides zero-copy shared-memory \
communication within ECUs for Software-Defined Vehicles."
HOMEPAGE = "https://github.com/eclipse-score/communication"
BUGTRACKER = "https://github.com/eclipse-score/communication/issues"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/eclipse-score/communication.git;protocol=https;branch=main;nobranch=0"
SRCREV = "d5414f75bfd4fc116572091ccca305d9e4b39338"

PV = "0.1.2"
S = "${WORKDIR}/git"

inherit bazel

# Bazel targets to build:
#  //score/mw/com - the core LoLa communication library
BAZEL_TARGETS = "//score/mw/com/... //score/mw/com/message_passing/..."

# Extra Bazel flags:
#  --jobs: Limit parallelism to avoid resource exhaustion in Yocto builds
#  --sandbox_strategy: Use local sandboxing which works better in containers
BAZEL_EXTRA_ARGS = " \
    --jobs=${@oe.utils.cpu_count()} \
    --sandbox_strategy=local \
    --verbose_failures \
"

# Output binaries/libraries produced by the Bazel build.
# Paths are relative to bazel-bin/ inside the source tree.
# NOTE: Update these paths after a successful build to match actual Bazel outputs.
BAZEL_OUTPUT_FILES = " \
    score/mw/com/libscore_mw_com.so \
"

DEPENDS += "bazel-native"

do_install:append() {
    # Install pkg-config file so downstream recipes can find the library
    install -d "${D}${libdir}/pkgconfig"
    cat > "${D}${libdir}/pkgconfig/score-communication.pc" << EOF
prefix=${prefix}
exec_prefix=${exec_prefix}
libdir=${libdir}
includedir=${includedir}

Name: score-communication
Description: Eclipse S-Core LoLa Communication Module
Version: ${PV}
Libs: -L\${libdir} -lscore_mw_com
Cflags: -I\${includedir}/score
EOF

    # Install public headers if present
    if [ -d "${S}/score/mw/com" ]; then
        find "${S}/score/mw/com" -name "*.h" | while read -r hdr; do
            rel_path="${hdr#${S}/}"
            install -D -m 644 "${hdr}" "${D}${includedir}/${rel_path}"
        done
    fi
}

FILES:${PN} += "${libdir}/libscore_mw_com.so"
FILES:${PN}-dev += "${libdir}/pkgconfig/score-communication.pc ${includedir}/score/mw/com/"

INSANE_SKIP:${PN} += "dev-so"
FILES_SOLIBSDEV = ""
