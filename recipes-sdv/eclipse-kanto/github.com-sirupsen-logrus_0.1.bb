DESCRIPTION = "Go module - github.com/sirupsen/logrus"
SRC_URI = "git://github.com/sirupsen/logrus;protocol=https;branch=master"
SRCREV = "${AUTOREV}"
LICENSE = "EPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/${PN}-${PV}/src/${GO_IMPORT}/LICENSE;md5=c7cc8aa73fb5717f8291fcec5ce9ed6c"

inherit go

GO_IMPORT = "github.com/sirupsen/logrus"
