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
#
# Generated with cargo-bitbake (see docs/new-rust-recipes.md); the crate list and
# the checksums are taken from the Cargo.lock committed in the upstream tree.
# Upstream has no tags or releases yet, hence SRCREV is pinned to a commit.

inherit cargo cargo-update-recipe-crates pkgconfig

SUMMARY = "SSAM package creation tool - Docker/OCI images to SSAM packages"
DESCRIPTION = "ssam-wrap converts OCI / Docker images (tar archives, image references) \
into SSAM packages that the ssam daemon can deploy."
HOMEPAGE = "https://github.com/mobis-oss/ssam"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a1885c272e2673d02fb53ba7338ffe76"

SRC_URI += "git://github.com/mobis-oss/ssam;protocol=https;branch=main"
SRCREV = "559a9346032b5b69290c91aafda3dbd6055dfe57"
PV = "0.1.0"

# Build only this workspace member of the SSAM monorepo.
CARGO_SRC_DIR = "ssam-wrap"
CARGO_BUILD_FLAGS += "-p ssam-wrap"
# Let BitBake split debug symbols instead of Cargo stripping the binary.
CARGO_BUILD_FLAGS += "--config profile.release.strip=false"

# libssam generates gRPC bindings with tonic-prost-build (protoc).
# zstd-sys links against the system libzstd via pkg-config.
DEPENDS += "protobuf-native zstd"

# Crate list shared by all three workspace members of the SSAM monorepo.
# Regenerate with: kas build --target ssam --cmd update_crates
require ssam-crates.inc
