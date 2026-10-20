# SPDX-License-Identifier: Apache-2.0

# Override the example configuration with Leda's machine-specific
# configuration.
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
RDEPENDS:${PN}:remove:qemux86-64 = "grub-editenv"
