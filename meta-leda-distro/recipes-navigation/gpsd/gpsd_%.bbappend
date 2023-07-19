FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://gpsd_default_config"

do_install:append() {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/gpsd_default_config ${D}${sysconfdir}/default/gpsd.default
}

SYSTEMD_SERVICE:${PN} = "${BPN}.service ${BPN}ctl@.service"
