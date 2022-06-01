FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:qemuarm64 = " file://fw_env.config"
SRC_URI:append:qemuarm64 = " file://boot.cmd.in"

#DEPENDS:append:qemuarm64 = " u-boot-default-script"

UBOOT_ENV_SUFFIX = "cmd"
UBOOT_ENV = "boot"

do_compile:append() {
    ${B}/tools/mkimage -C none -A arm64 -T script -d ${WORKDIR}/boot.cmd.in ${WORKDIR}/${UBOOT_ENV_BINARY}
}

do_install:append:qemuarm64 () {
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}
