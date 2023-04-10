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
DESCRIPTION = "Kantui"
SUMMARY = "A TUI for Kanto Container Management"
HOMEPAGE = "https://github.com/eclipse-leda/leda-utils/tree/main/src/rust/kanto-tui"

PROVIDES:${PN} += "kantui"
RPROVIDES:${PN} += "kantui"
inherit cargo

RDEPENDS_${PN} += "grpc protobuf nativesdk-protobuf"
DEPENDS += "protobuf protobuf-native grpc"

SRCREV_FORMAT = "kantuisrc_containerm"

SRC_URI += "git://github.com/eclipse-leda/leda-utils.git;protocol=https;name=kantuisrc;branch=main"
SRCREV_kantuisrc = "5bcbef1054775e277117d9cebacbc05234919ba7"
PV:append = ".AUTOINC+5bcbef1054"

# Fetch the Kanto Container Management repository since kantui needs the protobuf files from kanto CM
SRC_URI += "git://github.com/eclipse-kanto/container-management;protocol=https;branch=main;name=containerm;destsuffix=git/src/rust/kanto-tui/container-management"
SRCREV_containerm = "65e00358fa5aed1792059488a80fe755b36a8191"

S = "${WORKDIR}/git"
CARGO_SRC_DIR = "src/rust/kanto-tui"

RUSTC = "rustc"
PROTOC = "protoc --proto_path=/src/rust/kanto-tui/container-management/containerm"

SRC_URI += " \
    crate://crates.io/ahash/0.6.3 \
    crate://crates.io/ahash/0.7.6 \
    crate://crates.io/android_system_properties/0.1.5 \
    crate://crates.io/anyhow/1.0.68 \
    crate://crates.io/array-macro/1.0.5 \
    crate://crates.io/arrayvec/0.5.2 \
    crate://crates.io/async-priority-channel/0.1.0 \
    crate://crates.io/async-stream-impl/0.3.3 \
    crate://crates.io/async-stream/0.3.3 \
    crate://crates.io/async-trait/0.1.61 \
    crate://crates.io/atty/0.2.14 \
    crate://crates.io/autocfg/1.1.0 \
    crate://crates.io/axum-core/0.2.9 \
    crate://crates.io/axum/0.5.17 \
    crate://crates.io/base64/0.13.1 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/block-buffer/0.10.3 \
    crate://crates.io/bumpalo/3.11.1 \
    crate://crates.io/bytes/1.3.0 \
    crate://crates.io/cc/1.0.78 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/chrono/0.4.23 \
    crate://crates.io/clap/3.2.23 \
    crate://crates.io/clap_derive/3.2.18 \
    crate://crates.io/clap_lex/0.2.4 \
    crate://crates.io/codespan-reporting/0.11.1 \
    crate://crates.io/config/0.13.3 \
    crate://crates.io/core-foundation-sys/0.8.3 \
    crate://crates.io/cpufeatures/0.2.5 \
    crate://crates.io/crossbeam-channel/0.5.6 \
    crate://crates.io/crossbeam-utils/0.8.14 \
    crate://crates.io/crypto-common/0.1.6 \
    crate://crates.io/cursive/0.16.3 \
    crate://crates.io/cursive_buffered_backend/0.5.0 \
    crate://crates.io/cursive_core/0.2.2 \
    crate://crates.io/cursive_table_view/0.13.3 \
    crate://crates.io/cxx-build/1.0.86 \
    crate://crates.io/cxx/1.0.86 \
    crate://crates.io/cxxbridge-flags/1.0.86 \
    crate://crates.io/cxxbridge-macro/1.0.86 \
    crate://crates.io/darling/0.10.2 \
    crate://crates.io/darling_core/0.10.2 \
    crate://crates.io/darling_macro/0.10.2 \
    crate://crates.io/digest/0.10.6 \
    crate://crates.io/dlv-list/0.3.0 \
    crate://crates.io/either/1.8.0 \
    crate://crates.io/enclose/1.1.8 \
    crate://crates.io/enum-map-derive/0.4.6 \
    crate://crates.io/enum-map/0.6.6 \
    crate://crates.io/event-listener/2.5.3 \
    crate://crates.io/fastrand/1.8.0 \
    crate://crates.io/fixedbitset/0.4.2 \
    crate://crates.io/fnv/1.0.7 \
    crate://crates.io/futures-channel/0.3.25 \
    crate://crates.io/futures-core/0.3.25 \
    crate://crates.io/futures-sink/0.3.25 \
    crate://crates.io/futures-task/0.3.25 \
    crate://crates.io/futures-util/0.3.25 \
    crate://crates.io/generic-array/0.14.6 \
    crate://crates.io/getrandom/0.2.8 \
    crate://crates.io/h2/0.3.15 \
    crate://crates.io/hashbrown/0.12.3 \
    crate://crates.io/heck/0.4.0 \
    crate://crates.io/hermit-abi/0.1.19 \
    crate://crates.io/hermit-abi/0.2.6 \
    crate://crates.io/http-body/0.4.5 \
    crate://crates.io/http-range-header/0.3.0 \
    crate://crates.io/http/0.2.8 \
    crate://crates.io/httparse/1.8.0 \
    crate://crates.io/httpdate/1.0.2 \
    crate://crates.io/hyper-timeout/0.4.1 \
    crate://crates.io/hyper/0.14.23 \
    crate://crates.io/iana-time-zone-haiku/0.1.1 \
    crate://crates.io/iana-time-zone/0.1.53 \
    crate://crates.io/ident_case/1.0.1 \
    crate://crates.io/indexmap/1.9.2 \
    crate://crates.io/instant/0.1.12 \
    crate://crates.io/itertools/0.10.5 \
    crate://crates.io/itoa/1.0.5 \
    crate://crates.io/js-sys/0.3.60 \
    crate://crates.io/json5/0.4.1 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.139 \
    crate://crates.io/link-cplusplus/1.0.8 \
    crate://crates.io/linked-hash-map/0.5.6 \
    crate://crates.io/log/0.4.17 \
    crate://crates.io/matchit/0.5.0 \
    crate://crates.io/memchr/2.5.0 \
    crate://crates.io/memoffset/0.7.1 \
    crate://crates.io/mime/0.3.16 \
    crate://crates.io/minimal-lexical/0.2.1 \
    crate://crates.io/mio/0.8.5 \
    crate://crates.io/multimap/0.8.3 \
    crate://crates.io/nix/0.26.1 \
    crate://crates.io/nom/7.1.3 \
    crate://crates.io/num-complex/0.3.1 \
    crate://crates.io/num-integer/0.1.45 \
    crate://crates.io/num-iter/0.1.43 \
    crate://crates.io/num-rational/0.3.2 \
    crate://crates.io/num-traits/0.2.15 \
    crate://crates.io/num/0.3.1 \
    crate://crates.io/num_cpus/1.15.0 \
    crate://crates.io/numtoa/0.1.0 \
    crate://crates.io/once_cell/1.17.0 \
    crate://crates.io/ordered-multimap/0.4.3 \
    crate://crates.io/os_str_bytes/6.4.1 \
    crate://crates.io/owning_ref/0.4.1 \
    crate://crates.io/pathdiff/0.2.1 \
    crate://crates.io/percent-encoding/2.2.0 \
    crate://crates.io/pest/2.5.3 \
    crate://crates.io/pest_derive/2.5.3 \
    crate://crates.io/pest_generator/2.5.3 \
    crate://crates.io/pest_meta/2.5.3 \
    crate://crates.io/petgraph/0.6.2 \
    crate://crates.io/pin-project-internal/1.0.12 \
    crate://crates.io/pin-project-lite/0.2.9 \
    crate://crates.io/pin-project/1.0.12 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/ppv-lite86/0.2.17 \
    crate://crates.io/prettyplease/0.1.23 \
    crate://crates.io/proc-macro-error-attr/1.0.4 \
    crate://crates.io/proc-macro-error/1.0.4 \
    crate://crates.io/proc-macro2/1.0.50 \
    crate://crates.io/prost-build/0.11.6 \
    crate://crates.io/prost-derive/0.11.6 \
    crate://crates.io/prost-types/0.11.6 \
    crate://crates.io/prost/0.11.6 \
    crate://crates.io/quote/1.0.23 \
    crate://crates.io/rand/0.8.5 \
    crate://crates.io/rand_chacha/0.3.1 \
    crate://crates.io/rand_core/0.6.4 \
    crate://crates.io/redox_syscall/0.2.16 \
    crate://crates.io/redox_termios/0.1.2 \
    crate://crates.io/regex-syntax/0.6.28 \
    crate://crates.io/regex/1.7.1 \
    crate://crates.io/remove_dir_all/0.5.3 \
    crate://crates.io/ron/0.7.1 \
    crate://crates.io/rust-ini/0.18.0 \
    crate://crates.io/ryu/1.0.12 \
    crate://crates.io/scratch/1.0.3 \
    crate://crates.io/serde/1.0.152 \
    crate://crates.io/serde_derive/1.0.152 \
    crate://crates.io/serde_json/1.0.91 \
    crate://crates.io/sha2/0.10.6 \
    crate://crates.io/shlex/1.1.0 \
    crate://crates.io/signal-hook-registry/1.4.0 \
    crate://crates.io/signal-hook/0.3.14 \
    crate://crates.io/slab/0.4.7 \
    crate://crates.io/smallvec/1.10.0 \
    crate://crates.io/socket2/0.4.7 \
    crate://crates.io/stable_deref_trait/1.2.0 \
    crate://crates.io/static_assertions/1.1.0 \
    crate://crates.io/strip-ansi-escapes/0.1.1 \
    crate://crates.io/strsim/0.10.0 \
    crate://crates.io/strsim/0.9.3 \
    crate://crates.io/syn/1.0.107 \
    crate://crates.io/sync_wrapper/0.1.1 \
    crate://crates.io/tempfile/3.3.0 \
    crate://crates.io/termcolor/1.2.0 \
    crate://crates.io/termion/1.5.6 \
    crate://crates.io/textwrap/0.16.0 \
    crate://crates.io/thiserror-impl/1.0.38 \
    crate://crates.io/thiserror/1.0.38 \
    crate://crates.io/time/0.1.45 \
    crate://crates.io/tokio-io-timeout/1.2.0 \
    crate://crates.io/tokio-macros/1.8.2 \
    crate://crates.io/tokio-stream/0.1.11 \
    crate://crates.io/tokio-util/0.7.4 \
    crate://crates.io/tokio/1.24.1 \
    crate://crates.io/toml/0.5.10 \
    crate://crates.io/tonic-build/0.8.4 \
    crate://crates.io/tonic/0.8.2 \
    crate://crates.io/tower-http/0.3.5 \
    crate://crates.io/tower-layer/0.3.2 \
    crate://crates.io/tower-service/0.3.2 \
    crate://crates.io/tower/0.4.13 \
    crate://crates.io/tracing-attributes/0.1.23 \
    crate://crates.io/tracing-core/0.1.30 \
    crate://crates.io/tracing-futures/0.2.5 \
    crate://crates.io/tracing/0.1.37 \
    crate://crates.io/try-lock/0.2.4 \
    crate://crates.io/typenum/1.16.0 \
    crate://crates.io/ucd-trie/0.1.5 \
    crate://crates.io/unicode-ident/1.0.6 \
    crate://crates.io/unicode-segmentation/1.10.0 \
    crate://crates.io/unicode-width/0.1.10 \
    crate://crates.io/utf8parse/0.2.0 \
    crate://crates.io/version_check/0.9.4 \
    crate://crates.io/vte/0.10.1 \
    crate://crates.io/vte_generate_state_changes/0.1.1 \
    crate://crates.io/want/0.3.0 \
    crate://crates.io/wasi/0.10.0+wasi-snapshot-preview1 \
    crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
    crate://crates.io/wasm-bindgen-backend/0.2.83 \
    crate://crates.io/wasm-bindgen-macro-support/0.2.83 \
    crate://crates.io/wasm-bindgen-macro/0.2.83 \
    crate://crates.io/wasm-bindgen-shared/0.2.83 \
    crate://crates.io/wasm-bindgen/0.2.83 \
    crate://crates.io/wasmer_enumset/1.0.1 \
    crate://crates.io/wasmer_enumset_derive/0.5.0 \
    crate://crates.io/which/4.3.0 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.5 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/windows-sys/0.42.0 \
    crate://crates.io/windows_aarch64_gnullvm/0.42.1 \
    crate://crates.io/windows_aarch64_msvc/0.42.1 \
    crate://crates.io/windows_i686_gnu/0.42.1 \
    crate://crates.io/windows_i686_msvc/0.42.1 \
    crate://crates.io/windows_x86_64_gnu/0.42.1 \
    crate://crates.io/windows_x86_64_gnullvm/0.42.1 \
    crate://crates.io/windows_x86_64_msvc/0.42.1 \
    crate://crates.io/xi-unicode/0.3.0 \
    crate://crates.io/yaml-rust/0.4.5 \
"

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

include kantui.inc
