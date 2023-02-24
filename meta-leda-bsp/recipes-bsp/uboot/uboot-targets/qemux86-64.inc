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

FILESEXTRAPATHS:prepend:qemux86-64 := "${THISDIR}/../files:"
SRC_URI:append:qemux86-64 = " \
    file://fw_env.config \
    file://boot.cmd.in \
    file://virtio_env_in_fat_qemux86-64_efi.patch \
"

require conf/image-uefi.conf

do_configure:append:qemux86-64() {
    sed -e 's/@@KERNEL_IMAGETYPE@@/${KERNEL_IMAGETYPE}/' \
    -e 's/@@KERNEL_BOOTCMD@@/${KERNEL_BOOTCMD}/' \
    "${WORKDIR}/boot.cmd.in" > "${WORKDIR}/boot.cmd"
}

do_compile:append:qemux86-64() {
    ${B}/tools/mkimage -C none -A ${UBOOT_ARCH} -T script -d "${WORKDIR}/boot.cmd" "${UBOOT_ENV}.${UBOOT_ENV_SUFFIX}"
}

do_install:qemux86-64() {
    install -d ${D}${EFI_FILES_PATH}
    install -m 755 ${B}/u-boot-payload.efi ${D}${EFI_FILES_PATH}/${EFI_BOOT_IMAGE}

    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}

do_deploy:append:qemux86-64() {
    install -m 755 ${B}/u-boot-payload.efi ${DEPLOYDIR}
    ln -sf u-boot-payload.efi ${DEPLOYDIR}/${EFI_BOOT_IMAGE}

    install -m 644 ${WORKDIR}/boot.cmd ${DEPLOYDIR}
    install -m 644 ${B}/${UBOOT_ENV}.${UBOOT_ENV_SUFFIX} ${DEPLOYDIR}
}
