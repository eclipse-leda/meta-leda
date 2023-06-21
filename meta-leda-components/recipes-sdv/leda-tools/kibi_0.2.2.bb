# /********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
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

SUMMARY = "A text editor in less than 1024 lines of code with syntax higlighting, search and more."
DESCRIPTION = "Kibi is a small text editor with syntax highlighting and a permissive FOSS license. Can be used as a nano-replacement"
HOMEPAGE = "https://github.com/ilai-deutel/kibi"

inherit cargo

SRC_URI += "git://github.com/ilai-deutel/kibi.git;protocol=https;nobranch=1"
SRCREV = "750daa33946502ab15e57cdb982787140f0e53d3"

S = "${WORKDIR}/git"
CARGO_SRC_DIR = ""
PV:append = ".AUTOINC+750daa3394"

KIBI_ALIAS_NANO ??= "1"
KIBI_ALIAS_NANO[doc] = "Set to 0 to skip aliasing nano to kibi. Default 1 (kibi is supposed to be a nano replacement)."

SRC_URI += " \
    crate://crates.io/bitflags/1.3.2 \
    crate://crates.io/cc/1.0.79 \
    crate://crates.io/cfg-if/1.0.0 \
    crate://crates.io/dashmap/5.2.0 \
    crate://crates.io/errno-dragonfly/0.1.2 \
    crate://crates.io/errno/0.3.0 \
    crate://crates.io/fastrand/1.7.0 \
    crate://crates.io/futures-channel/0.3.21 \
    crate://crates.io/futures-core/0.3.21 \
    crate://crates.io/futures-executor/0.3.21 \
    crate://crates.io/futures-io/0.3.21 \
    crate://crates.io/futures-sink/0.3.21 \
    crate://crates.io/futures-task/0.3.21 \
    crate://crates.io/futures-util/0.3.21 \
    crate://crates.io/futures/0.3.21 \
    crate://crates.io/hermit-abi/0.1.19 \
    crate://crates.io/instant/0.1.12 \
    crate://crates.io/io-lifetimes/1.0.5 \
    crate://crates.io/lazy_static/1.4.0 \
    crate://crates.io/libc/0.2.144 \
    crate://crates.io/linux-raw-sys/0.3.1 \
    crate://crates.io/lock_api/0.4.6 \
    crate://crates.io/log/0.4.17 \
    crate://crates.io/memchr/2.5.0 \
    crate://crates.io/num_cpus/1.13.1 \
    crate://crates.io/parking_lot/0.12.1 \
    crate://crates.io/parking_lot_core/0.9.3 \
    crate://crates.io/pin-project-lite/0.2.9 \
    crate://crates.io/pin-utils/0.1.0 \
    crate://crates.io/proc-macro2/1.0.56 \
    crate://crates.io/quote/1.0.27 \
    crate://crates.io/redox_syscall/0.2.10 \
    crate://crates.io/redox_syscall/0.3.5 \
    crate://crates.io/rustix/0.37.6 \
    crate://crates.io/scopeguard/1.1.0 \
    crate://crates.io/serial_test/2.0.0 \
    crate://crates.io/serial_test_derive/2.0.0 \
    crate://crates.io/slab/0.4.6 \
    crate://crates.io/smallvec/1.8.0 \
    crate://crates.io/syn/2.0.15 \
    crate://crates.io/tempfile/3.5.0 \
    crate://crates.io/unicode-ident/1.0.8 \
    crate://crates.io/unicode-width/0.1.10 \
    crate://crates.io/winapi-i686-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi-util/0.1.5 \
    crate://crates.io/winapi-x86_64-pc-windows-gnu/0.4.0 \
    crate://crates.io/winapi/0.3.9 \
    crate://crates.io/windows-sys/0.36.1 \
    crate://crates.io/windows-sys/0.45.0 \
    crate://crates.io/windows-targets/0.42.1 \
    crate://crates.io/windows_aarch64_gnullvm/0.42.1 \
    crate://crates.io/windows_aarch64_msvc/0.36.1 \
    crate://crates.io/windows_aarch64_msvc/0.42.1 \
    crate://crates.io/windows_i686_gnu/0.36.1 \
    crate://crates.io/windows_i686_gnu/0.42.1 \
    crate://crates.io/windows_i686_msvc/0.36.1 \
    crate://crates.io/windows_i686_msvc/0.42.1 \
    crate://crates.io/windows_x86_64_gnu/0.36.1 \
    crate://crates.io/windows_x86_64_gnu/0.42.1 \
    crate://crates.io/windows_x86_64_gnullvm/0.42.1 \
    crate://crates.io/windows_x86_64_msvc/0.36.1 \
    crate://crates.io/windows_x86_64_msvc/0.42.1 \
"

# We choose the Apache-2.0 license
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE-APACHE;md5=2f53897b0b4f171c36c885dafd675e66"

include kibi.inc
