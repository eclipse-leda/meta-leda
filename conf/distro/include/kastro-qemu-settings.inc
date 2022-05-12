# Increase memory to 2GB for k3s
QB_MEM = "-m 2048"

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
QB_KERNEL_ROOT:qemuarm = "/dev/vda4"
QB_DRIVE_TYPE:qemuarm="/dev/vd"

# wic nogaphic ovmf
