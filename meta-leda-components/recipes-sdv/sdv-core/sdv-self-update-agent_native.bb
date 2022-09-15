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

DESCRIPTION = "Native build for self-update-agent."
DEPENDS = "git-native"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a832eda17114b48ae16cda6a500941c2"

SRC_URI = "https://github.com/SoftwareDefinedVehicle/sdv-self-update-agent.git;branch=main"
SRCREV  = "844406d9392efa2487723b539925b5354adf3cb2"

S = "${WORKDIR}/git"

do_packagedata[noexec] = "1"
do_package_qa[noexec] = "1"
do_package_write_ipk[noexec] = "1"

FILES:${PN} += "${bindir}/sdv-self-update-agent"
FILES:${PN} += "${libdir}/libcrypto.so.3"
FILES:${PN} += "${libdir}/libcurl.so"
FILES:${PN} += "${libdir}/libgmock_main.so"
FILES:${PN} += "${libdir}/libgmock_main.so.1.11.0"
FILES:${PN} += "${libdir}/libgmock.so"
FILES:${PN} += "${libdir}/libgmock.so.1.11.0"
FILES:${PN} += "${libdir}/libgtest_main.so"
FILES:${PN} += "${libdir}/libgtest_main.so.1.11.0"
FILES:${PN} += "${libdir}/libgtest.so"
FILES:${PN} += "${libdir}/libgtest.so.1.11.0"
FILES:${PN} += "${libdir}/libmini-yaml.so"
FILES:${PN} += "${libdir}/libpaho-mqtt3a.so"
FILES:${PN} += "${libdir}/libpaho-mqtt3a.so.1"
FILES:${PN} += "${libdir}/libpaho-mqtt3a.so.1.3.10"
FILES:${PN} += "${libdir}/libpaho-mqtt3c.so"
FILES:${PN} += "${libdir}/libpaho-mqtt3c.so.1"
FILES:${PN} += "${libdir}/libpaho-mqtt3c.so.1.3.10"
FILES:${PN} += "${libdir}/libpaho-mqttpp3.so"
FILES:${PN} += "${libdir}/libpaho-mqttpp3.so.1"
FILES:${PN} += "${libdir}/libpaho-mqttpp3.so.1.2.0"
FILES:${PN} += "${libdir}/libssl.so.3"

do_compile() {
    cd ${S}
    case ${TARGET_ARCH} in
        aarch64*)    
            ./scripts/build_openssl_arm64.sh
            ./scripts/build_glib_arm64.sh
            cd build_arm64
            cmake -DCMAKE_INSTALL_PREFIX=../dist_arm64 -DCMAKE_TOOLCHAIN_FILE=../cmake/linux/arm64/toolchain.cmake -DOPENSSL_ROOT_DIR=../build_arm64 -DOPENSSL_CRYPTO_LIBRARY=../build_arm64/lib/libcrypto.so ..
            make install
						;;
        x86_64*)
            ./scripts/build_openssl_amd64.sh
            ./scripts/build_glib_amd64.sh
            cd build_amd64
            cmake -DCMAKE_INSTALL_PREFIX=../dist_amd64 -DCMAKE_TOOLCHAIN_FILE=../cmake/linux/amd64/toolchain.cmake -DOPENSSL_ROOT_DIR=../build_amd64 -DOPENSSL_CRYPTO_LIBRARY=../build_amd64/lib/libcrypto.so ..
            make install
						;;
        *)
            bbfatal "Recipe has no target-arch-to-dapr-release-arch mapping for '${TARGET_ARCH}'."
            ;;
    esac
}

do_install() {
    cd ${S}
    install -d ${D}/usr
    case ${TARGET_ARCH} in
        aarch64*)    
            cp -r dist_arm64 ${D}/usr/sua
            ;;
        x86_64*)
            cp -r dist_amd64 ${D}/usr/sua
            ;;
        *)
            bbfatal "Recipe has no target-arch-to-dapr-release-arch mapping for '${TARGET_ARCH}'."
            ;;
    esac
}
