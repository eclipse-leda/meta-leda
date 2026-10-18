
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://config-crun.toml \
            file://config-runc-default.toml \
            file://containerd_service_depends_on_data.patch \
            "

WITH_SYSTEMD = " ${@bb.utils.contains("DISTRO_FEATURES", "systemd", "true", "", d)}"

do_install:append() {
    install -d ${D}${sysconfdir}/containerd
    
    if [ -n "${WITH_SYSTEMD}" ]
    then
        # when systemd is available, crun is installed, use that config
        install -m 0644 ${WORKDIR}/config-crun.toml ${D}${sysconfdir}/containerd/config.toml    
    else
        # otherwise fall back to default runc one
        install -m 0644 ${WORKDIR}/config-runc-default.toml ${D}${sysconfdir}/containerd/config.toml 
    fi

}

FILES:${PN} += "${sysconfdir}/containerd/config.toml"
