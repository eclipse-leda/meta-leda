SUMMARY = "Initial boot script"
DESCRIPTION = "Script to do any first boot init, started as a systemd service which removes itself once finished"
LICENSE = "Apache-2.0"

inherit systemd features_check

SRC_URI =  " \
    file://LICENSE \
    file://first-boot-initservice/ \
"

LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "initscript.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "${sbindir}/initscript.sh \
                ${systemd_system_unitdir}/initscript.service"

REQUIRED_DISTRO_FEATURES = "systemd"

do_install:append () {
    install -d ${D}/${sbindir}
    install -m 0755 ${WORKDIR}/first-boot-initservice/initscript.sh ${D}/${sbindir}

    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/first-boot-initservice/initscript.service ${D}${systemd_system_unitdir}
}
