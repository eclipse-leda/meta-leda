FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qemuarm64 = " file://fw_env.config"
SRC_URI:append:qemuarm64 = " file://boot.cmd.in"

#DEPENDS:append:qemuarm64 = " u-boot-default-script"

UBOOT_ENV_SUFFIX = "cmd"
UBOOT_ENV = "boot"

UBOOT_EXTLINUX_LABELS = "full minimal rescue"

UBOOT_EXTLINUX_DEFAULT_LABEL = "full"
UBOOT_EXTLINUX_TIMEOUT = "30"

UBOOT_EXTLINUX_KERNEL_IMAGE_full = "/boot/Image"
UBOOT_EXTLINUX_MENU_DESCRIPTION_full ??= "SDV Full"
UBOOT_EXTLINUX_KERNEL_ARGS_full = "root=/dev/vda4 rootwait loglevel=7 rauc.slot=rootfs_full"

UBOOT_EXTLINUX_KERNEL_IMAGE_minimal ??= "/boot/Image"
UBOOT_EXTLINUX_MENU_DESCRIPTION_minimal ??= "SDV Minimal"
UBOOT_EXTLINUX_KERNEL_ARGS_minimal = "root=/dev/vda5 rootwait loglevel=6 rauc.slot=rootfs_minimal"

UBOOT_EXTLINUX_KERNEL_IMAGE_rescue ??= "/boot/Image"
UBOOT_EXTLINUX_MENU_DESCRIPTION_rescue ??= "SDV Rescue"
UBOOT_EXTLINUX_KERNEL_ARGS_rescue = "root=/dev/vda3 rootwait loglevel=0 rauc.slot=rootfs_rescue"

do_compile:append() {
    ${B}/tools/mkimage -C none -A arm64 -T script -d ${WORKDIR}/boot.cmd.in ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_install:append:qemuarm64 () {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}
