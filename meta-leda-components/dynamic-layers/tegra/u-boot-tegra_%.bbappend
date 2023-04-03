OVERRIDES .= "${@bb.utils.contains('DISTRO_FEATURES', 'rauc', ':rauc-integration', '', d)}"
#inherit rauc-integration

FILESEXTRAPATHS:prepend:rauc-integration := "${THISDIR}/files:"
SRC_URI:append:rauc-integration = " file://boot.cmd.in"
