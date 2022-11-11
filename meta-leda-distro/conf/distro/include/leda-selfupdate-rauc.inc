# /********************************************************************************
# * Copyright (c) 2022 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************/

# Machine specific, see meta-leda/meta-leda-bsp/conf/machine/qemux86-64-extra.conf
# Enable GRUB Bootloader, so that we can chose which partition to boot.
# MACHINE_FEATURES:append = " pcbios efi"
# EXTRA_IMAGEDEPENDS += "ovmf"
# PREFERRED_RPROVIDER_virtual-grub-bootconf = "rauc-qemu-grubconf"
