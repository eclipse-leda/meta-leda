ALTERNATIVE_PRIORITY[sh] = "110"
RPROVIDES:${PN}:append:class-target = " ${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/bin/sh', '', d)}"
