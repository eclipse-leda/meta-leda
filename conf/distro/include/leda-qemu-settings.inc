# Increase memory for k3s to at least 2GB or more
QB_MEM = "-m 4096"

# No graphical user interface
QB_OPT_APPEND:append = " -serial mon:stdio -serial null -nographic"

# Emulate a CAN-Bus device
QB_OPT_APPEND:append = " -object can-bus,id=canbus0 "
QB_OPT_APPEND:append = " -device kvaser_pci,canbus=canbus0"

QB_DEFAULT_FSTYPE = "ext4"
