# /********************************************************************************
# * Copyright (c) 2023-2026 Contributors to the Eclipse Foundation
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

SRC_URI[bitflags-1.3.2.sha256sum] = "bef38d45163c2f1dde094a7dfd33ccf595c92905c8f8f4fdc18d06fb1037718a"
SRC_URI[cc-1.0.79.sha256sum] = "50d30906286121d95be3d479533b458f87493b30a4b5f79a607db8f5d11aa91f"
SRC_URI[cfg-if-1.0.0.sha256sum] = "baf1de4339761588bc0619e3cbc0120ee582ebb74b53b4efbf79117bd2da40fd"
SRC_URI[dashmap-5.2.0.sha256sum] = "4c8858831f7781322e539ea39e72449c46b059638250c14344fec8d0aa6e539c"
SRC_URI[errno-dragonfly-0.1.2.sha256sum] = "aa68f1b12764fab894d2755d2518754e71b4fd80ecfb822714a1206c2aab39bf"
SRC_URI[errno-0.3.0.sha256sum] = "50d6a0976c999d473fe89ad888d5a284e55366d9dc9038b1ba2aa15128c4afa0"
SRC_URI[fastrand-1.7.0.sha256sum] = "c3fcf0cee53519c866c09b5de1f6c56ff9d647101f81c1964fa632e148896cdf"
SRC_URI[futures-channel-0.3.21.sha256sum] = "c3083ce4b914124575708913bca19bfe887522d6e2e6d0952943f5eac4a74010"
SRC_URI[futures-core-0.3.21.sha256sum] = "0c09fd04b7e4073ac7156a9539b57a484a8ea920f79c7c675d05d289ab6110d3"
SRC_URI[futures-executor-0.3.21.sha256sum] = "9420b90cfa29e327d0429f19be13e7ddb68fa1cccb09d65e5706b8c7a749b8a6"
SRC_URI[futures-io-0.3.21.sha256sum] = "fc4045962a5a5e935ee2fdedaa4e08284547402885ab326734432bed5d12966b"
SRC_URI[futures-sink-0.3.21.sha256sum] = "21163e139fa306126e6eedaf49ecdb4588f939600f0b1e770f4205ee4b7fa868"
SRC_URI[futures-task-0.3.21.sha256sum] = "57c66a976bf5909d801bbef33416c41372779507e7a6b3a5e25e4749c58f776a"
SRC_URI[futures-util-0.3.21.sha256sum] = "d8b7abd5d659d9b90c8cba917f6ec750a74e2dc23902ef9cd4cc8c8b22e6036a"
SRC_URI[futures-0.3.21.sha256sum] = "f73fe65f54d1e12b726f517d3e2135ca3125a437b6d998caf1962961f7172d9e"
SRC_URI[hermit-abi-0.1.19.sha256sum] = "62b467343b94ba476dcb2500d242dadbb39557df889310ac77c5d99100aaac33"
SRC_URI[instant-0.1.12.sha256sum] = "7a5bbe824c507c5da5956355e86a746d82e0e1464f65d862cc5e71da70e94b2c"
SRC_URI[io-lifetimes-1.0.5.sha256sum] = "1abeb7a0dd0f8181267ff8adc397075586500b81b28a73e8a0208b00fc170fb3"
SRC_URI[lazy_static-1.4.0.sha256sum] = "e2abad23fbc42b3700f2f279844dc832adb2b2eb069b2df918f455c4e18cc646"
SRC_URI[libc-0.2.144.sha256sum] = "2b00cc1c228a6782d0f076e7b232802e0c5689d41bb5df366f2a6b6621cfdfe1"
SRC_URI[linux-raw-sys-0.3.1.sha256sum] = "d59d8c75012853d2e872fb56bc8a2e53718e2cafe1a4c823143141c6d90c322f"
SRC_URI[lock_api-0.4.6.sha256sum] = "88943dd7ef4a2e5a4bfa2753aaab3013e34ce2533d1996fb18ef591e315e2b3b"
SRC_URI[log-0.4.17.sha256sum] = "abb12e687cfb44aa40f41fc3978ef76448f9b6038cad6aef4259d3c095a2382e"
SRC_URI[memchr-2.5.0.sha256sum] = "2dffe52ecf27772e601905b7522cb4ef790d2cc203488bbd0e2fe85fcb74566d"
SRC_URI[num_cpus-1.13.1.sha256sum] = "19e64526ebdee182341572e50e9ad03965aa510cd94427a4549448f285e957a1"
SRC_URI[parking_lot-0.12.1.sha256sum] = "3742b2c103b9f06bc9fff0a37ff4912935851bee6d36f3c02bcc755bcfec228f"
SRC_URI[parking_lot_core-0.9.3.sha256sum] = "09a279cbf25cb0757810394fbc1e359949b59e348145c643a939a525692e6929"
SRC_URI[pin-project-lite-0.2.9.sha256sum] = "e0a7ae3ac2f1173085d398531c705756c94a4c56843785df85a60c1a0afac116"
SRC_URI[pin-utils-0.1.0.sha256sum] = "8b870d8c151b6f2fb93e84a13146138f05d02ed11c7e7c54f8826aaaf7c9f184"
SRC_URI[proc-macro2-1.0.56.sha256sum] = "2b63bdb0cd06f1f4dedf69b254734f9b45af66e4a031e42a7480257d9898b435"
SRC_URI[quote-1.0.27.sha256sum] = "8f4f29d145265ec1c483c7c654450edde0bfe043d3938d6972630663356d9500"
SRC_URI[redox_syscall-0.2.10.sha256sum] = "8383f39639269cde97d255a32bdb68c047337295414940c68bdd30c2e13203ff"
SRC_URI[redox_syscall-0.3.5.sha256sum] = "567664f262709473930a4bf9e51bf2ebf3348f2e748ccc50dea20646858f8f29"
SRC_URI[rustix-0.37.6.sha256sum] = "d097081ed288dfe45699b72f5b5d648e5f15d64d900c7080273baa20c16a6849"
SRC_URI[scopeguard-1.1.0.sha256sum] = "d29ab0c6d3fc0ee92fe66e2d99f700eab17a8d57d1c1d3b748380fb20baa78cd"
SRC_URI[serial_test-2.0.0.sha256sum] = "0e56dd856803e253c8f298af3f4d7eb0ae5e23a737252cd90bb4f3b435033b2d"
SRC_URI[serial_test_derive-2.0.0.sha256sum] = "91d129178576168c589c9ec973feedf7d3126c01ac2bf08795109aa35b69fb8f"
SRC_URI[slab-0.4.6.sha256sum] = "eb703cfe953bccee95685111adeedb76fabe4e97549a58d16f03ea7b9367bb32"
SRC_URI[smallvec-1.8.0.sha256sum] = "f2dd574626839106c320a323308629dcb1acfc96e32a8cba364ddc61ac23ee83"
SRC_URI[syn-2.0.15.sha256sum] = "a34fcf3e8b60f57e6a14301a2e916d323af98b0ea63c599441eec8558660c822"
SRC_URI[tempfile-3.5.0.sha256sum] = "b9fbec84f381d5795b08656e4912bec604d162bff9291d6189a78f4c8ab87998"
SRC_URI[unicode-ident-1.0.8.sha256sum] = "e5464a87b239f13a63a501f2701565754bae92d243d4bb7eb12f6d57d2269bf4"
SRC_URI[unicode-width-0.1.10.sha256sum] = "c0edd1e5b14653f783770bce4a4dabb4a5108a5370a5f5d8cfe8710c361f6c8b"
SRC_URI[winapi-i686-pc-windows-gnu-0.4.0.sha256sum] = "ac3b87c63620426dd9b991e5ce0329eff545bccbbb34f3be09ff6fb6ab51b7b6"
SRC_URI[winapi-util-0.1.5.sha256sum] = "70ec6ce85bb158151cae5e5c87f95a8e97d2c0c4b001223f33a334e3ce5de178"
SRC_URI[winapi-x86_64-pc-windows-gnu-0.4.0.sha256sum] = "712e227841d057c1ee1cd2fb22fa7e5a5461ae8e48fa2ca79ec42cfc1931183f"
SRC_URI[winapi-0.3.9.sha256sum] = "5c839a674fcd7a98952e593242ea400abe93992746761e38641405d28b00f419"
SRC_URI[windows-sys-0.36.1.sha256sum] = "ea04155a16a59f9eab786fe12a4a450e75cdb175f9e0d80da1e17db09f55b8d2"
SRC_URI[windows-sys-0.45.0.sha256sum] = "75283be5efb2831d37ea142365f009c02ec203cd29a3ebecbc093d52315b66d0"
SRC_URI[windows-targets-0.42.1.sha256sum] = "8e2522491fbfcd58cc84d47aeb2958948c4b8982e9a2d8a2a35bbaed431390e7"
SRC_URI[windows_aarch64_gnullvm-0.42.1.sha256sum] = "8c9864e83243fdec7fc9c5444389dcbbfd258f745e7853198f365e3c4968a608"
SRC_URI[windows_aarch64_msvc-0.36.1.sha256sum] = "9bb8c3fd39ade2d67e9874ac4f3db21f0d710bee00fe7cab16949ec184eeaa47"
SRC_URI[windows_aarch64_msvc-0.42.1.sha256sum] = "4c8b1b673ffc16c47a9ff48570a9d85e25d265735c503681332589af6253c6c7"
SRC_URI[windows_i686_gnu-0.36.1.sha256sum] = "180e6ccf01daf4c426b846dfc66db1fc518f074baa793aa7d9b9aaeffad6a3b6"
SRC_URI[windows_i686_gnu-0.42.1.sha256sum] = "de3887528ad530ba7bdbb1faa8275ec7a1155a45ffa57c37993960277145d640"
SRC_URI[windows_i686_msvc-0.36.1.sha256sum] = "e2e7917148b2812d1eeafaeb22a97e4813dfa60a3f8f78ebe204bcc88f12f024"
SRC_URI[windows_i686_msvc-0.42.1.sha256sum] = "bf4d1122317eddd6ff351aa852118a2418ad4214e6613a50e0191f7004372605"
SRC_URI[windows_x86_64_gnu-0.36.1.sha256sum] = "4dcd171b8776c41b97521e5da127a2d86ad280114807d0b2ab1e462bc764d9e1"
SRC_URI[windows_x86_64_gnu-0.42.1.sha256sum] = "c1040f221285e17ebccbc2591ffdc2d44ee1f9186324dd3e84e99ac68d699c45"
SRC_URI[windows_x86_64_gnullvm-0.42.1.sha256sum] = "628bfdf232daa22b0d64fdb62b09fcc36bb01f05a3939e20ab73aaf9470d0463"
SRC_URI[windows_x86_64_msvc-0.36.1.sha256sum] = "c811ca4a8c853ef420abd8592ba53ddbbac90410fab6903b3e79972a631f7680"
SRC_URI[windows_x86_64_msvc-0.42.1.sha256sum] = "447660ad36a13288b1db4d4248e857b510e8c3a225c822ba4fb748c0aafecffd"

# We choose the Apache-2.0 license
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE-APACHE;md5=2f53897b0b4f171c36c885dafd675e66"

include kibi.inc
