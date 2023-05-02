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

# Enable needed distro features
DISTRO_FEATURES:append = " virtualization"
DISTRO_FEATURES:append = " seccomp"
DISTRO_FEATURES:append = " rauc"
DISTRO_FEATURES:append = " security"
DISTRO_FEATURES:append = " sdv"
DISTRO_FEATURES:append = " ldconfig"

# Enable WiFi
DISTRO_FEATURES:append = " wifi"

# Enable systemd as init process
DISTRO_FEATURES:append = " systemd"
VIRTUAL-RUNTIME_init_manager = "systemd"
DISTRO_FEATURES_BACKFILL_CONSIDERED = "sysvinit"

# Disable unneeded distro features inherited from poky.
DISTRO_FEATURES:remove = "alsa"
DISTRO_FEATURES:remove = "bluetooth"
DISTRO_FEATURES:remove = "cramfs"
DISTRO_FEATURES:remove = "directfb"
DISTRO_FEATURES:remove = "3g"
DISTRO_FEATURES:remove = "opengl"
DISTRO_FEATURES:remove = "wayland"
DISTRO_FEATURES:remove = "x11"
DISTRO_FEATURES:remove = "nfc"
DISTRO_FEATURES:remove = "nfs"
DISTRO_FEATURES:remove = "ext2"
DISTRO_FEATURES:remove = "pcmcia"
DISTRO_FEATURES:remove = "pulseaudio"
DISTRO_FEATURES:remove = "ppp"
DISTRO_FEATURES:remove = "smbfs"
DISTRO_FEATURES:remove = "debuginfod"
DISTRO_FEATURES:remove = "ptest"
