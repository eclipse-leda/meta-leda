# /********************************************************************************
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
# ********************************************************************************/

SUMMARY = "Eclipse Ankaios server - workload and container orchestration for automotive HPCs"
DESCRIPTION = "Eclipse Ankaios provides workload and container orchestration for \
automotive High Performance Computing Software (HPCs). The server component \
manages the desired state of the system and coordinates with agents."
HOMEPAGE = "https://eclipse-ankaios.github.io/ankaios"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit cargo

SRC_URI += "git://github.com/eclipse-ankaios/ankaios.git;protocol=https;branch=main"
SRCREV = "1a84057f3b46ef88b4674d7b7f791a86e0c3f446"
PV = "0.4.0"
S = "${WORKDIR}/git"
CARGO_SRC_DIR = "server"

DEPENDS += "protobuf protobuf-native"

SRC_URI += " \
    crate://crates.io/addr2line/0.22.0 \
    crate://crates.io/adler/1.0.2 \
    crate://crates.io/aho-corasick/1.1.3 \
    crate://crates.io/anstream/0.6.14 \
    crate://crates.io/anstyle-parse/0.2.4 \
    crate://crates.io/anstyle-query/1.1.0 \
    crate://crates.io/anstyle-wincon/3.0.3 \
    crate://crates.io/anstyle/1.0.7 \
    crate://crates.io/anyhow/1.0.86 \
    crate://crates.io/asn1-rs-derive/0.5.0 \
    crate://crates.io/asn1-rs-impl/0.2.0 \
    crate://crates.io/asn1-rs/0.6.1 \
    crate://crates.io/async-stream-impl/0.3.5 \
    crate://crates.io/async-stream/0.3.5 \
    crate://crates.io/async-trait/0.1.80 \
    crate://crates.io/autocfg/1.3.0 \
    crate://crates.io/axum-core/0.3.4 \
    crate://crates.io/axum/0.6.20 \
    crate://crates.io/backtrace/0.3.72 \
    crate://crates.io/base64/0.21.7 \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/bitflags/2.5.0 \
    crate://crates.io/block-buffer/0.10.4 \
    crate://crates.io/bytecount/0.6.8 \
    crate://crates.io/bytes/1.6.0 \
    crate://crates.io/cc/1.0.99 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/clap/4.5.7 \
    crate://crates.io/clap_builder/4.5.7 \
    crate://crates.io/clap_derive/4.5.5 \
    crate://crates.io/clap_lex/0.7.1 \
    crate://crates.io/colorchoice/1.0.1 \
    crate://crates.io/cpufeatures/0.2.12 \
    crate://crates.io/crossterm/0.27.0 \
    crate://crates.io/crossterm_winapi/0.9.1 \
    crate://crates.io/crypto-common/0.1.6 \
    crate://crates.io/data-encoding/2.6.0 \
    crate://crates.io/der-parser/9.0.0 \
    crate://crates.io/deranged/0.3.11 \
    crate://crates.io/difflib/0.4.0 \
    crate://crates.io/digest/0.10.7 \
    crate://crates.io/displaydoc/0.2.4 \
    crate://crates.io/downcast/0.11.0 \
    crate://crates.io/either/1.12.0 \
    crate://crates.io/env_logger/0.10.2 \
    crate://crates.io/equivalent/1.0.1 \
    crate://crates.io/errno/0.3.9 \
    crate://crates.io/fastrand/2.1.0 \
    crate://crates.io/fixedbitset/0.4.2 \
    crate://crates.io/float-cmp/0.9.0 \
    crate://crates.io/fnv/1.0.7 \
    crate://crates.io/fragile/2.0.0 \
    crate://crates.io/futures-channel/0.3.30 \
    crate://crates.io/futures-core/0.3.30 \
    crate://crates.io/futures-macro/0.3.30 \
    crate://crates.io/futures-sink/0.3.30 \
    crate://crates.io/futures-task/0.3.30 \
    crate://crates.io/futures-util/0.3.30 \
    crate://crates.io/generic-array/0.14.7 \
    crate://crates.io/getrandom/0.2.15 \
    crate://crates.io/gimli/0.29.0 \
    crate://crates.io/h2/0.3.26 \
    crate://crates.io/hashbrown/0.12.3 \
    crate://crates.io/hashbrown/0.14.5 \
    crate://crates.io/heck/0.4.1 \
    crate://crates.io/heck/0.5.0 \
    crate://crates.io/hermit-abi/0.3.9 \
    crate://crates.io/hex/0.4.3 \
    crate://crates.io/home/0.5.9 \
    crate://crates.io/http-body/0.4.6 \
    crate://crates.io/http/0.2.12 \
    crate://crates.io/httparse/1.9.2 \
    crate://crates.io/httpdate/1.0.3 \
    crate://crates.io/humantime/2.1.0 \
    crate://crates.io/hyper-timeout/0.4.1 \
    crate://crates.io/hyper/0.14.29 \
    crate://crates.io/hyperlocal/0.8.0 \
    crate://crates.io/indexmap/1.9.3 \
    crate://crates.io/indexmap/2.2.6 \
    crate://crates.io/is-terminal/0.4.12 \
    crate://crates.io/is_terminal_polyfill/1.70.0 \
    crate://crates.io/itertools/0.10.5 \
    crate://crates.io/itoa/1.0.11 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.155 \
    crate://crates.io/linux-raw-sys/0.4.14 \
    crate://crates.io/lock_api/0.4.12 \
    crate://crates.io/log/0.4.21 \
    crate://crates.io/matchit/0.7.3 \
    crate://crates.io/memchr/2.7.2 \
    crate://crates.io/memoffset/0.7.1 \
    crate://crates.io/mime/0.3.17 \
    crate://crates.io/minimal-lexical/0.2.1 \
    crate://crates.io/miniz_oxide/0.7.3 \
    crate://crates.io/mio/0.8.11 \
    crate://crates.io/mockall/0.11.4 \
    crate://crates.io/mockall_derive/0.11.4 \
    crate://crates.io/mockall_double/0.3.1 \
    crate://crates.io/multimap/0.8.3 \
    crate://crates.io/nix/0.26.4 \
    crate://crates.io/nom/7.1.3 \
    crate://crates.io/normalize-line-endings/0.3.0 \
    crate://crates.io/num-bigint/0.4.5 \
    crate://crates.io/num-conv/0.1.0 \
    crate://crates.io/num-integer/0.1.46 \
    crate://crates.io/num-traits/0.2.19 \
    crate://crates.io/num_cpus/1.16.0 \
    crate://crates.io/object/0.35.0 \
    crate://crates.io/oid-registry/0.7.0 \
    crate://crates.io/once_cell/1.19.0 \
    crate://crates.io/papergrid/0.9.1 \
    crate://crates.io/parking_lot/0.12.3 \
    crate://crates.io/parking_lot_core/0.9.10 \
    crate://crates.io/percent-encoding/2.3.1 \
    crate://crates.io/petgraph/0.6.5 \
    crate://crates.io/pin-project-internal/1.1.5 \
    crate://crates.io/pin-project-lite/0.2.14 \
    crate://crates.io/pin-project/1.1.5 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/powerfmt/0.2.0 \
    crate://crates.io/ppv-lite86/0.2.17 \
    crate://crates.io/predicates-core/1.0.6 \
    crate://crates.io/predicates-tree/1.0.9 \
    crate://crates.io/predicates/2.1.5 \
    crate://crates.io/prettyplease/0.1.25 \
    crate://crates.io/proc-macro-error-attr/1.0.4 \
    crate://crates.io/proc-macro-error/1.0.4 \
    crate://crates.io/proc-macro2/1.0.85 \
    crate://crates.io/prost-build/0.11.9 \
    crate://crates.io/prost-derive/0.11.9 \
    crate://crates.io/prost-types/0.11.9 \
    crate://crates.io/prost/0.11.9 \
    crate://crates.io/quote/1.0.36 \
    crate://crates.io/rand/0.8.5 \
    crate://crates.io/rand_chacha/0.3.1 \
    crate://crates.io/rand_core/0.6.4 \
    crate://crates.io/redox_syscall/0.5.1 \
    crate://crates.io/regex-automata/0.4.7 \
    crate://crates.io/regex-syntax/0.8.4 \
    crate://crates.io/regex/1.10.5 \
    crate://crates.io/ring/0.17.8 \
    crate://crates.io/rustc-demangle/0.1.24 \
    crate://crates.io/rusticata-macros/4.1.0 \
    crate://crates.io/rustix/0.38.34 \
    crate://crates.io/rustls-pemfile/1.0.4 \
    crate://crates.io/rustls-webpki/0.101.7 \
    crate://crates.io/rustls/0.21.12 \
    crate://crates.io/rustversion/1.0.17 \
    crate://crates.io/ryu/1.0.18 \
    crate://crates.io/scopeguard/1.2.0 \
    crate://crates.io/sct/0.7.1 \
    crate://crates.io/serde/1.0.203 \
    crate://crates.io/serde_derive/1.0.203 \
    crate://crates.io/serde_json/1.0.117 \
    crate://crates.io/serde_yaml/0.9.34+deprecated \
    crate://crates.io/sha2/0.10.8 \
    crate://crates.io/sha256/1.5.0 \
    crate://crates.io/signal-hook-mio/0.2.3 \
    crate://crates.io/signal-hook-registry/1.4.2 \
    crate://crates.io/signal-hook/0.3.17 \
    crate://crates.io/slab/0.4.9 \
    crate://crates.io/smallvec/1.13.2 \
    crate://crates.io/socket2/0.5.7 \
    crate://crates.io/spin/0.9.8 \
    crate://crates.io/strsim/0.11.1 \
    crate://crates.io/syn/1.0.109 \
    crate://crates.io/syn/2.0.66 \
    crate://crates.io/sync_wrapper/0.1.2 \
    crate://crates.io/synstructure/0.13.1 \
    crate://crates.io/tabled/0.12.2 \
    crate://crates.io/tabled_derive/0.6.0 \
    crate://crates.io/tempfile/3.10.1 \
    crate://crates.io/termcolor/1.4.1 \
    crate://crates.io/termtree/0.4.1 \
    crate://crates.io/thiserror-impl/1.0.61 \
    crate://crates.io/thiserror/1.0.61 \
    crate://crates.io/time-core/0.1.2 \
    crate://crates.io/time-macros/0.2.18 \
    crate://crates.io/time/0.3.36 \
    crate://crates.io/tokio-io-timeout/1.2.0 \
    crate://crates.io/tokio-macros/2.3.0 \
    crate://crates.io/tokio-rustls/0.24.1 \
    crate://crates.io/tokio-stream/0.1.15 \
    crate://crates.io/tokio-util/0.7.11 \
    crate://crates.io/tokio/1.38.0 \
    crate://crates.io/tonic-build/0.9.2 \
    crate://crates.io/tonic/0.9.2 \
    crate://crates.io/tower-layer/0.3.2 \
    crate://crates.io/tower-service/0.3.2 \
    crate://crates.io/tower/0.4.13 \
    crate://crates.io/tracing-attributes/0.1.27 \
    crate://crates.io/tracing-core/0.1.32 \
    crate://crates.io/tracing/0.1.40 \
    crate://crates.io/try-lock/0.2.5 \
    crate://crates.io/typenum/1.17.0 \
    crate://crates.io/umask/2.1.0 \
    crate://crates.io/unicode-ident/1.0.12 \
    crate://crates.io/unicode-width/0.1.13 \
    crate://crates.io/unsafe-libyaml/0.2.11 \
    crate://crates.io/untrusted/0.9.0 \
    crate://crates.io/utf8parse/0.2.2 \
    crate://crates.io/uuid/1.8.0 \
    crate://crates.io/version_check/0.9.4 \
    crate://crates.io/want/0.3.1 \
    crate://crates.io/wasi/0.11.0+wasi-snapshot-preview1 \
    crate://crates.io/which/4.4.2 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.8 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/windows-sys/0.48.0 \
    crate://crates.io/windows-sys/0.52.0 \
    crate://crates.io/windows-targets/0.48.5 \
    crate://crates.io/windows-targets/0.52.5 \
    crate://crates.io/windows_aarch64_gnullvm/0.48.5 \
    crate://crates.io/windows_aarch64_gnullvm/0.52.5 \
    crate://crates.io/windows_aarch64_msvc/0.48.5 \
    crate://crates.io/windows_aarch64_msvc/0.52.5 \
    crate://crates.io/windows_i686_gnu/0.48.5 \
    crate://crates.io/windows_i686_gnu/0.52.5 \
    crate://crates.io/windows_i686_gnullvm/0.52.5 \
    crate://crates.io/windows_i686_msvc/0.48.5 \
    crate://crates.io/windows_i686_msvc/0.52.5 \
    crate://crates.io/windows_x86_64_gnu/0.48.5 \
    crate://crates.io/windows_x86_64_gnu/0.52.5 \
    crate://crates.io/windows_x86_64_gnullvm/0.48.5 \
    crate://crates.io/windows_x86_64_gnullvm/0.52.5 \
    crate://crates.io/windows_x86_64_msvc/0.48.5 \
    crate://crates.io/windows_x86_64_msvc/0.52.5 \
    crate://crates.io/x509-parser/0.16.0 \
"
