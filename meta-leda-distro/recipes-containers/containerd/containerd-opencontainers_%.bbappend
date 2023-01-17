
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://config.toml \
            file://containerd_service_depends_on_data.patch \
            "

do_install:append() {
    install -d ${D}${sysconfdir}/containerd
    install -m 0644 ${WORKDIR}/config.toml ${D}${sysconfdir}/containerd/config.toml
}

FILES:${PN} += "${sysconfdir}/containerd/config.toml"
