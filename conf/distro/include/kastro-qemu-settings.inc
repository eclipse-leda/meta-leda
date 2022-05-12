# Increase memory to 2GB for k3s
QB_MEM = "-m 2048"

# No graphical user interface
QB_OPT_APPEND:append = " -serial mon:stdio -serial null -nographic"

# Emulate a CAN-Bus device
QB_OPT_APPEND:append = " -object can-bus,id=canbus0 "
QB_OPT_APPEND:append = " -device kvaser_pci,canbus=canbus0"

QB_DEFAULT_FSTYPE = "wic.qcow2"

# Fall back to ext4 for now, as wic for qemuarm does not yet contain u-boot
QB_DEFAULT_FSTYPE:qemuarm = "ext4"
QB_FSINFO:qemuarm = "wic:no-kernel-in-fs"

# wic nogaphic ovmf
