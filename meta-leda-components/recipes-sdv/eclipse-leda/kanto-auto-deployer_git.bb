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

SRCREV_FORMAT = "kadsrc_containerm"

PV:append = ".AUTOINC+cead2d028f"
SRC_URI += "git://github.com/eclipse-leda/leda-utils.git;protocol=https;name=kadsrc;branch=main"
SRCREV_kadsrc = "cead2d028fb011f124b0f321e2c3a7c4e1845c12"

# Fetch the Kanto Container Management repository since kanto-auto-deployer needs the protobuf files from kanto CM
SRC_URI += "git://github.com/eclipse-kanto/container-management;protocol=https;branch=main;name=containerm;destsuffix=git/src/rust/kanto-auto-deployer/container-management"
SRCREV_containerm = "65e00358fa5aed1792059488a80fe755b36a8191"

S = "${WORKDIR}/git"

CARGO_SRC_DIR = "src/rust/kanto-auto-deployer"

RUSTC = "rustc"
PROTOC = "protoc --proto_path=/src/rust/kanto-auto-deployer/container-management/containerm"

# please note if you have entries that do not begin with crate://
# you must change them to how that package can be fetched
SRC_URI += " \
    crate://crates.io/aho-corasick/0.7.20 \
    crate://crates.io/anyhow/1.0.70 \
    crate://crates.io/async-stream-impl/0.3.4 \
    crate://crates.io/async-stream/0.3.4 \
    crate://crates.io/async-trait/0.1.68 \
    crate://crates.io/atty/0.2.14 \
    crate://crates.io/autocfg/1.1.0 \
    crate://crates.io/axum-core/0.2.9 \
    crate://crates.io/axum/0.5.17 \
    crate://crates.io/base64/0.13.1 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/bytes/1.4.0 \
    crate://crates.io/cc/1.0.79 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/clap/3.2.23 \
    crate://crates.io/clap_derive/3.2.18 \
    crate://crates.io/clap_lex/0.2.4 \
    crate://crates.io/cmake/0.1.50 \
    crate://crates.io/crossbeam-channel/0.5.7 \
    crate://crates.io/crossbeam-utils/0.8.15 \
    crate://crates.io/either/1.8.1 \
    crate://crates.io/enclose/1.1.8 \
    crate://crates.io/env_logger/0.9.3 \
    crate://crates.io/errno-dragonfly/0.1.2 \
    crate://crates.io/errno/0.3.0 \
    crate://crates.io/fastrand/1.9.0 \
    crate://crates.io/filetime/0.2.20 \
    crate://crates.io/fixedbitset/0.4.2 \
    crate://crates.io/fnv/1.0.7 \
    crate://crates.io/fsevent-sys/4.1.0 \
    crate://crates.io/futures-channel/0.3.27 \
    crate://crates.io/futures-core/0.3.27 \
    crate://crates.io/futures-sink/0.3.27 \
    crate://crates.io/futures-task/0.3.27 \
    crate://crates.io/futures-util/0.3.27 \
    crate://crates.io/getrandom/0.2.8 \
    crate://crates.io/glob/0.3.1 \
    crate://crates.io/h2/0.3.16 \
    crate://crates.io/hashbrown/0.12.3 \
    crate://crates.io/heck/0.4.1 \
    crate://crates.io/hermit-abi/0.1.19 \
    crate://crates.io/hermit-abi/0.2.6 \
    crate://crates.io/hermit-abi/0.3.1 \
    crate://crates.io/http-body/0.4.5 \
    crate://crates.io/http-range-header/0.3.0 \
    crate://crates.io/http/0.2.9 \
    crate://crates.io/httparse/1.8.0 \
    crate://crates.io/httpdate/1.0.2 \
    crate://crates.io/humantime/2.1.0 \
    crate://crates.io/hyper-timeout/0.4.1 \
    crate://crates.io/hyper/0.14.25 \
    crate://crates.io/indexmap/1.9.3 \
    crate://crates.io/inotify-sys/0.1.5 \
    crate://crates.io/inotify/0.9.6 \
    crate://crates.io/instant/0.1.12 \
    crate://crates.io/io-lifetimes/1.0.9 \
    crate://crates.io/itertools/0.10.5 \
    crate://crates.io/itoa/1.0.6 \
    crate://crates.io/json-patch/0.3.0 \
    crate://crates.io/kqueue-sys/1.0.3 \
    crate://crates.io/kqueue/1.0.7 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.140 \
    crate://crates.io/linux-raw-sys/0.3.0 \
    crate://crates.io/log/0.4.17 \
    crate://crates.io/matchit/0.5.0 \
    crate://crates.io/memchr/2.5.0 \
    crate://crates.io/mime/0.3.17 \
    crate://crates.io/mio/0.8.6 \
    crate://crates.io/multimap/0.8.3 \
    crate://crates.io/notify/5.1.0 \
    crate://crates.io/num_cpus/1.15.0 \
    crate://crates.io/once_cell/1.17.1 \
    crate://crates.io/os_str_bytes/6.5.0 \
    crate://crates.io/percent-encoding/2.2.0 \
    crate://crates.io/petgraph/0.6.3 \
    crate://crates.io/pin-project-internal/1.0.12 \
    crate://crates.io/pin-project-lite/0.2.9 \
    crate://crates.io/pin-project/1.0.12 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/ppv-lite86/0.2.17 \
    crate://crates.io/prettyplease/0.1.25 \
    crate://crates.io/proc-macro-error-attr/1.0.4 \
    crate://crates.io/proc-macro-error/1.0.4 \
    crate://crates.io/proc-macro2/1.0.54 \
    crate://crates.io/prost-build/0.10.4 \
    crate://crates.io/prost-derive/0.10.1 \
    crate://crates.io/prost-types/0.10.1 \
    crate://crates.io/prost/0.10.4 \
    crate://crates.io/quote/1.0.26 \
    crate://crates.io/rand/0.8.5 \
    crate://crates.io/rand_chacha/0.3.1 \
    crate://crates.io/rand_core/0.6.4 \
    crate://crates.io/redox_syscall/0.2.16 \
    crate://crates.io/redox_syscall/0.3.5 \
    crate://crates.io/regex-syntax/0.6.29 \
    crate://crates.io/regex/1.7.3 \
    crate://crates.io/rustix/0.37.5 \
    crate://crates.io/ryu/1.0.13 \
    crate://crates.io/same-file/1.0.6 \
    crate://crates.io/serde/1.0.159 \
    crate://crates.io/serde_derive/1.0.159 \
    crate://crates.io/serde_json/1.0.95 \
    crate://crates.io/slab/0.4.8 \
    crate://crates.io/socket2/0.4.9 \
    crate://crates.io/strsim/0.10.0 \
    crate://crates.io/syn/1.0.109 \
    crate://crates.io/syn/2.0.11 \
    crate://crates.io/sync_wrapper/0.1.1 \
    crate://crates.io/tempfile/3.5.0 \
    crate://crates.io/termcolor/1.2.0 \
    crate://crates.io/textwrap/0.16.0 \
    crate://crates.io/thiserror-impl/1.0.40 \
    crate://crates.io/thiserror/1.0.40 \
    crate://crates.io/tokio-io-timeout/1.2.0 \
    crate://crates.io/tokio-macros/2.0.0 \
    crate://crates.io/tokio-stream/0.1.12 \
    crate://crates.io/tokio-util/0.7.7 \
    crate://crates.io/tokio/1.27.0 \
    crate://crates.io/tonic-build/0.7.2 \
    crate://crates.io/tonic/0.7.2 \
    crate://crates.io/tower-http/0.3.5 \
    crate://crates.io/tower-layer/0.3.2 \
    crate://crates.io/tower-service/0.3.2 \
    crate://crates.io/tower/0.4.13 \
    crate://crates.io/tracing-attributes/0.1.23 \
    crate://crates.io/tracing-core/0.1.30 \
    crate://crates.io/tracing-futures/0.2.5 \
    crate://crates.io/tracing/0.1.37 \
    crate://crates.io/try-lock/0.2.4 \
    crate://crates.io/unicode-ident/1.0.8 \
    crate://crates.io/version_check/0.9.4 \
    crate://crates.io/walkdir/2.3.3 \
    crate://crates.io/want/0.3.0 \
    crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
    crate://crates.io/which/4.4.0 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.5 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/windows-sys/0.42.0 \
    crate://crates.io/windows-sys/0.45.0 \
    crate://crates.io/windows-targets/0.42.2 \
    crate://crates.io/windows_aarch64_gnullvm/0.42.2 \
    crate://crates.io/windows_aarch64_msvc/0.42.2 \
    crate://crates.io/windows_i686_gnu/0.42.2 \
    crate://crates.io/windows_i686_msvc/0.42.2 \
    crate://crates.io/windows_x86_64_gnu/0.42.2 \
    crate://crates.io/windows_x86_64_gnullvm/0.42.2 \
    crate://crates.io/windows_x86_64_msvc/0.42.2 \
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
