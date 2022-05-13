# Increase memory for k3s to at least 2GB or more
QB_MEM = "-m 4096"

# No graphical user interface
QB_OPT_APPEND:append = " -serial mon:stdio -serial null -nographic"

# Emulate a CAN-Bus device
QB_OPT_APPEND:append = " -object can-bus,id=canbus0 "
QB_OPT_APPEND:append = " -device kvaser_pci,canbus=canbus0"

QB_DEFAULT_FSTYPE = "wic.qcow2"

# Fall back to ext4 for now, as wic for qemuarm does not yet contain u-boot
QB_DEFAULT_FSTYPE:qemuarm = "wic.qcow2"
QB_ROOTFS_OPT:qemuarm = "-drive id=disk0,file=@ROOTFS@,if=none,format=qcow2 -device virtio-blk-device,drive=disk0"
QB_FSINFO:qemuarm = "wic:no-kernel-in-fs"

# Must be in sync with:
#  GRUB Config: meta-sdv/recipes-bsp/grub/files/grub.cfg
#  WIC Kickstarter File: build-sdv-arm-qemu/wic/qemuarm-grub.wks
QB_KERNEL_ROOT:qemuarm = "/dev/vda4"
QB_DRIVE_TYPE:qemuarm="/dev/vd"

# Must be in sync with 
#  GRUB Config: meta-sdv/recipes-bsp/grub/files/grub.cfg
#  WIC Kickstarter File: build-sdv-x86_64/wic/qemux86-grub-efi.wks
QB_KERNEL_ROOT:qemux86-64 = "/dev/vda4"
QB_DRIVE_TYPE:qemux86-64="/dev/vd"

# wic nogaphic ovmf
