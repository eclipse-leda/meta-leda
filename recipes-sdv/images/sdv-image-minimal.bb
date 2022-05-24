SUMMARY = "A full quickstart image with all features enabled."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_INSTALL:append = " packagegroup-sdv-tools"
IMAGE_INSTALL:append = " packagegroup-sdv-examples"

IMAGE_LINGUAS = " "

LICENSE = "EPL"

inherit core-image

IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
