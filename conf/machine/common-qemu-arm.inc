EXTRA_IMAGEDEPENDS += "u-boot"
do_image_wic[depends] += "mtools-native:do_populate_sysroot dosfstools-native:do_populate_sysroot virtual/bootloader:do_deploy"

#SERIAL_CONSOLES = "115200;ttyS0"

KERNEL_IMAGETYPE = "Image"
KERNEL_EXTRA_ARGS += "LOADADDR=${UBOOT_ENTRYPOINT}"

UBOOT_SUFFIX = "bin"
UBOOT_ENTRYPOINT = "0x80008000"
UBOOT_LOADADDRESS = "0x80008000"

MACHINE_FEATURES = "usbgadget usbhost vfat"

IMAGE_BOOT_FILES = "u-boot.${UBOOT_SUFFIX} ${KERNEL_IMAGETYPE}"
IMAGE_CLASSES += "qemuboot"

QB_DEFAULT_BIOS = "u-boot.bin"
