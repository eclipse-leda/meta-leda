SUMMARY = "bitbake-layers recipe"
DESCRIPTION = "Recipe created by bitbake-layers"
LICENSE = "EPL"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/swdc-os-cloud-agent.git"
SRCREV = "28ce9e78a1fa5867c982e11d380f4f617fb97d60"

# Replace 'xxx' after first build with correct value
LIC_FILES_CHKSUM = "file://NOTICE;md5=xxx"

python do_display_banner() {
    bb.plain("***********************************************");
    bb.plain("*                                             *");
    bb.plain("*  Example recipe created by bitbake-layers   *");
    bb.plain("*                                             *");
    bb.plain("***********************************************");
}

addtask display_banner before do_build
