SUMMARY = "SDV Core Self Update Agent"
DESCRIPTION = "Bridge to RAUC Self Updater"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/sdv-edge-core-utils;branch=main;rev=main"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=2dd765ca47a05140be15ebafddbeadfe"
 
RDEPENDS_${PN} += "bash"

do_install() {
    install -d ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/* ${D}${bindir}
}

FILES_${PN} += "${datadir}/${PN}/src/bash/ \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"