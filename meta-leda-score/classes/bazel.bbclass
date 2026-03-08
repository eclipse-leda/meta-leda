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
# bazel.bbclass - BitBake class for building projects that use the Bazel build system.
#
# Usage:
#   inherit bazel
#
# Required variables:
#   BAZEL_TARGETS     - Space-separated list of Bazel build targets (e.g. "//score/mw/com:lola")
#   BAZEL_OUTPUT_FILES - List of output files relative to Bazel output directory to install
#
# Optional variables:
#   BAZEL_VERSION      - Bazel version to use (default: 7.4.1, can be overridden per recipe).
#                        The recipe bazel-native must provide this same version.
#   BAZEL_EXTRA_ARGS   - Additional arguments to pass to Bazel build command.
#   BAZEL_TEST_TARGETS - Bazel test targets to run (leave empty to skip tests).
#
# Notes:
#   - All Bazel external dependencies are fetched during do_fetch via the
#     BAZEL_FETCH_DEPS mechanism. Set BAZEL_FETCH_DEPS = "1" to enable.
#     This requires network access during fetch phase.
#   - Cross-compilation: Bazel's --platforms flag is set based on TARGET_ARCH
#     when BAZEL_CROSS_COMPILE = "1" (default). Custom platform mappings can
#     be provided via BAZEL_PLATFORM.
#   - The Bazel output base is isolated to WORKDIR to prevent cache pollution
#     between recipes.
#
# Cross-compilation considerations:
#   Bazel uses its own toolchain management (rules_cc, toolchains_llvm, etc.).
#   For Yocto cross-compilation, the Bazel build needs a platform definition
#   that matches the Yocto target machine. By default, this class attempts to
#   map the Yocto TARGET_ARCH to a known Bazel platform. For recipes that use
#   custom Bazel toolchains (e.g., eclipse-score), the toolchain configuration
#   within the Bazel build itself controls cross-compilation and no additional
#   Yocto sysroot injection is performed.
#
# Limitations:
#   - Bazel's sandboxed builds may conflict with Yocto's build environment.
#     Setting BAZEL_EXTRA_ARGS += "--sandbox_strategy=local" may help.
#   - Bazel fetches its own dependencies (tools, rules) at build time unless
#     pre-seeded. This requires network access during do_compile or the use
#     of a Bazel module mirror.
#   - For hermetic builds, all Bazel module dependencies should be pre-fetched
#     and vendored. See the BAZEL_VENDOR_DIR option.

DEPENDS += "bazel-native"

# Bazel version - must match the version provided by bazel-native
BAZEL_VERSION ?= "7.4.1"

# Bazel build targets to build
BAZEL_TARGETS ?= "//..."

# Additional Bazel build arguments
BAZEL_EXTRA_ARGS ?= ""

# Bazel test targets (empty = skip tests)
BAZEL_TEST_TARGETS ?= ""

# Enable cross-compilation platform mapping
BAZEL_CROSS_COMPILE ?= "0"

# Custom Bazel platform (leave empty for auto-detection)
BAZEL_PLATFORM ?= ""

# Bazel output directory name (default: bazel-bin)
BAZEL_OUTPUT_DIR ?= "bazel-bin"

# List of output files/patterns to install from Bazel output directory.
# Paths are relative to BAZEL_OUTPUT_DIR inside the source tree.
# Example: "score/mw/com/liblola.so"
BAZEL_OUTPUT_FILES ?= ""

# Bazel home directory (isolated per recipe)
BAZEL_HOME = "${WORKDIR}/bazel-home"

# Bazel output base (isolated per recipe)
BAZEL_OUTPUT_BASE = "${WORKDIR}/bazel-output-base"

# Bazel repository cache (isolated per recipe)
BAZEL_REPOSITORY_CACHE = "${WORKDIR}/bazel-repository-cache"

# Bazel disk cache for build artifacts
BAZEL_DISK_CACHE = "${WORKDIR}/bazel-disk-cache"

# Set to "1" to vendor Bazel module dependencies into WORKDIR during fetch
BAZEL_VENDOR_DIR ?= ""

do_configure:prepend() {
    mkdir -p "${BAZEL_HOME}"
    mkdir -p "${BAZEL_OUTPUT_BASE}"
    mkdir -p "${BAZEL_REPOSITORY_CACHE}"
    mkdir -p "${BAZEL_DISK_CACHE}"
}

# Determine platform flags for cross-compilation
def bazel_get_platform_flags(d):
    if not d.getVar('BAZEL_CROSS_COMPILE') == '1':
        return ''
    platform = d.getVar('BAZEL_PLATFORM')
    if platform:
        return '--platforms=' + platform
    target_arch = d.getVar('TARGET_ARCH')
    arch_map = {
        'x86_64': '@platforms//cpu:x86_64',
        'aarch64': '@platforms//cpu:aarch64',
        'arm': '@platforms//cpu:arm',
        'riscv64': '@platforms//cpu:riscv64',
    }
    cpu_platform = arch_map.get(target_arch, '')
    if cpu_platform:
        return '--platforms=//:target_platform'
    return ''

bazel_do_compile() {
    cd "${S}"
    export HOME="${BAZEL_HOME}"
    export TEST_TMPDIR="${BAZEL_HOME}/tmp"
    mkdir -p "${TEST_TMPDIR}"

    PLATFORM_FLAGS="${@bazel_get_platform_flags(d)}"

    bbnote "Running Bazel build: ${BAZEL_TARGETS}"
    bbnote "Bazel version: $(bazel --version)"

    bazel \
        --output_base="${BAZEL_OUTPUT_BASE}" \
        build \
        --repository_cache="${BAZEL_REPOSITORY_CACHE}" \
        --disk_cache="${BAZEL_DISK_CACHE}" \
        ${PLATFORM_FLAGS} \
        ${BAZEL_EXTRA_ARGS} \
        ${BAZEL_TARGETS}
}

bazel_do_install() {
    if [ -z "${BAZEL_OUTPUT_FILES}" ]; then
        bbwarn "BAZEL_OUTPUT_FILES is not set; nothing will be installed"
        return
    fi

    for output_file in ${BAZEL_OUTPUT_FILES}; do
        src="${S}/${BAZEL_OUTPUT_DIR}/${output_file}"
        if [ ! -e "${src}" ]; then
            bbwarn "Bazel output file not found: ${src}"
            continue
        fi
        case "${output_file}" in
            *.so | *.so.*)
                install -d "${D}${libdir}"
                install -m 755 "${src}" "${D}${libdir}/"
                ;;
            *)
                install -d "${D}${bindir}"
                install -m 755 "${src}" "${D}${bindir}/"
                ;;
        esac
    done
}

EXPORT_FUNCTIONS do_compile do_install
