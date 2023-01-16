FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://mosquitto.conf"

do_install:append() {
  install -m 0644 ${WORKDIR}/mosquitto.conf ${D}${sysconfdir}/mosquitto
  install -d ${D}${sysconfdir}/mosquitto/conf.d
}
