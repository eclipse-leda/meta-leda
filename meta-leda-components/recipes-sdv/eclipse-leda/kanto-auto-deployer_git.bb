# /********************************************************************************
# * Copyright (c) 2022 Contributors to the Eclipse Foundation
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
DESCRIPTION = "Kanto Auto Deployer"
inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_PACKAGES = "${@bb.utils.contains('DISTRO_FEATURES','systemd','${PN}','',d)}"
SYSTEMD_SERVICE:${PN} = "${@bb.utils.contains('DISTRO_FEATURES','systemd','kanto-auto-deployer.service','',d)}"

PROVIDES:${PN} += "kanto-auto-deployer"
RPROVIDES:${PN} += "kanto-auto-deployer"

inherit cargo kanto-auto-deployer

RDEPENDS_${PN} += " grpc protobuf nativesdk-protobuf"
DEPENDS += " protobuf protobuf-native grpc git-native"

SRCREV = "9f68b40d7880261ee04450f20ad1e3b59ef5a692"
PV:append = ".AUTOINC+9f68b40d788"
SRC_URI = "gitsm://github.com/eclipse-leda/leda-utils;protocol=https;nobranch=0;branch=main"

S = "${WORKDIR}/git"

CARGO_SRC_DIR = "src/rust/kanto-auto-deployer"

RUSTC = "rustc"
PROTOC = "protoc --proto_path=/src/rust/kanto-auto-deployer/container-management/containerm"

# please note if you have entries that do not begin with crate://
# you must change them to how that package can be fetched
SRC_URI += " \
    crate://crates.io/anyhow/1.0.66 \
    crate://crates.io/async-stream-impl/0.3.3 \
    crate://crates.io/async-stream/0.3.3 \
    crate://crates.io/async-trait/0.1.59 \
    crate://crates.io/autocfg/1.1.0 \
    crate://crates.io/axum-core/0.2.9 \
    crate://crates.io/axum/0.5.17 \
    crate://crates.io/base64/0.13.1 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/bytes/1.3.0 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/either/1.8.0 \
    crate://crates.io/fastrand/1.8.0 \
    crate://crates.io/fixedbitset/0.4.2 \
    crate://crates.io/fnv/1.0.7 \
    crate://crates.io/futures-channel/0.3.25 \
    crate://crates.io/futures-core/0.3.25 \
    crate://crates.io/futures-sink/0.3.25 \
    crate://crates.io/futures-task/0.3.25 \
    crate://crates.io/futures-util/0.3.25 \
    crate://crates.io/getrandom/0.2.8 \
    crate://crates.io/glob/0.3.0 \
    crate://crates.io/h2/0.3.15 \
    crate://crates.io/hashbrown/0.12.3 \
    crate://crates.io/heck/0.4.0 \
    crate://crates.io/hermit-abi/0.1.19 \
    crate://crates.io/http-body/0.4.5 \
    crate://crates.io/http-range-header/0.3.0 \
    crate://crates.io/http/0.2.8 \
    crate://crates.io/httparse/1.8.0 \
    crate://crates.io/httpdate/1.0.2 \
    crate://crates.io/hyper-timeout/0.4.1 \
    crate://crates.io/hyper/0.14.23 \
    crate://crates.io/indexmap/1.9.2 \
    crate://crates.io/instant/0.1.12 \
    crate://crates.io/itertools/0.10.5 \
    crate://crates.io/itoa/1.0.4 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.137 \
    crate://crates.io/log/0.4.17 \
    crate://crates.io/matchit/0.5.0 \
    crate://crates.io/memchr/2.5.0 \
    crate://crates.io/mime/0.3.16 \
    crate://crates.io/mio/0.8.5 \
    crate://crates.io/multimap/0.8.3 \
    crate://crates.io/num_cpus/1.14.0 \
    crate://crates.io/once_cell/1.16.0 \
    crate://crates.io/percent-encoding/2.2.0 \
    crate://crates.io/petgraph/0.6.2 \
    crate://crates.io/pin-project-internal/1.0.12 \
    crate://crates.io/pin-project-lite/0.2.9 \
    crate://crates.io/pin-project/1.0.12 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/ppv-lite86/0.2.17 \
    crate://crates.io/prettyplease/0.1.21 \
    crate://crates.io/proc-macro2/1.0.47 \
    crate://crates.io/prost-build/0.11.3 \
    crate://crates.io/prost-derive/0.11.2 \
    crate://crates.io/prost-types/0.11.2 \
    crate://crates.io/prost/0.11.3 \
    crate://crates.io/quote/1.0.21 \
    crate://crates.io/rand/0.8.5 \
    crate://crates.io/rand_chacha/0.3.1 \
    crate://crates.io/rand_core/0.6.4 \
    crate://crates.io/redox_syscall/0.2.16 \
    crate://crates.io/regex-syntax/0.6.28 \
    crate://crates.io/regex/1.7.0 \
    crate://crates.io/remove_dir_all/0.5.3 \
    crate://crates.io/ryu/1.0.11 \
    crate://crates.io/serde/1.0.148 \
    crate://crates.io/serde_derive/1.0.148 \
    crate://crates.io/serde_json/1.0.89 \
    crate://crates.io/slab/0.4.7 \
    crate://crates.io/socket2/0.4.7 \
    crate://crates.io/syn/1.0.105 \
    crate://crates.io/sync_wrapper/0.1.1 \
    crate://crates.io/tempfile/3.3.0 \
    crate://crates.io/tokio-io-timeout/1.2.0 \
    crate://crates.io/tokio-macros/1.8.2 \
    crate://crates.io/tokio-stream/0.1.11 \
    crate://crates.io/tokio-util/0.7.4 \
    crate://crates.io/tokio/1.22.0 \
    crate://crates.io/tonic-build/0.8.4 \
    crate://crates.io/tonic/0.8.2 \
    crate://crates.io/tower-http/0.3.4 \
    crate://crates.io/tower-layer/0.3.2 \
    crate://crates.io/tower-service/0.3.2 \
    crate://crates.io/tower/0.4.13 \
    crate://crates.io/tracing-attributes/0.1.23 \
    crate://crates.io/tracing-core/0.1.30 \
    crate://crates.io/tracing-futures/0.2.5 \
    crate://crates.io/tracing/0.1.37 \
    crate://crates.io/try-lock/0.2.3 \
    crate://crates.io/unicode-ident/1.0.5 \
    crate://crates.io/want/0.3.0 \
    crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
    crate://crates.io/which/4.3.0 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/windows-sys/0.42.0 \
    crate://crates.io/windows_aarch64_gnullvm/0.42.0 \
    crate://crates.io/windows_aarch64_msvc/0.42.0 \
    crate://crates.io/windows_i686_gnu/0.42.0 \
    crate://crates.io/windows_i686_msvc/0.42.0 \
    crate://crates.io/windows_x86_64_gnu/0.42.0 \
    crate://crates.io/windows_x86_64_gnullvm/0.42.0 \
    crate://crates.io/windows_x86_64_msvc/0.42.0 \
"

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SUMMARY = "Kanto Auto Deployer via a unix socket"
HOMEPAGE = "https://github.com/eclipse-leda/leda-utils/tree/main/src/rust/kanto-auto-deployer"

# includes this file if it exists but does not fail
# this is useful for anything you may want to override from
# what cargo-bitbake generates.
include kanto-auto-deployer.inc
