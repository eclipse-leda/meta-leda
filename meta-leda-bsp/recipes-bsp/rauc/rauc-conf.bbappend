# SPDX-License-Identifier: Apache-2.0

# Override the GRUB demo configuration with Leda's U-Boot configuration.
FILESEXTRAPATHS:prepend:qemux86-64 := "${THISDIR}/files:"
RDEPENDS:${PN}:remove:qemux86-64 = "grub-editenv"
