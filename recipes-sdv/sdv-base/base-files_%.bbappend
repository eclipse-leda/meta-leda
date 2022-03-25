do_install_basefilesissue:append() {
    # Overwrite /etc/issue with a custom version of it
    LAYER_REV=$(git describe --tags)
    printf "${DISTRO_NAME} ${LAYER_REV}" > ${D}${sysconfdir}/issue
}
