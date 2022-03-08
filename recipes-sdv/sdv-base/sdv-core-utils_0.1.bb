SUMMARY = "SDV Core Utilities"
DESCRIPTION = "Core shell scripts"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/sdv-edge-core-utils;branch=main;rev=main"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=2dd765ca47a05140be15ebafddbeadfe"

# Runtime Dependencies
RDEPENDS:${PN} += " bash"

# Force to refetch every time
do_fetch[nostamp] = "1"

# Skip the compilation tasks
#do_compile[noexec] = "1"
#do_configure[noexec] = "1"
#do_package_qa[noexec] = "1"

do_install() {
    install -d ${D}${bindir}
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/git/src/bash/* ${D}${bindir}
}

FILES_${PN} += "${bindir}/${PN}/src/bash/ \
                ${bindir}/${PN}/LICENSE"

PACKAGES = "${PN}"
