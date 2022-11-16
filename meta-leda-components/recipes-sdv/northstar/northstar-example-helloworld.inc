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
#root@qemux86-64:~# northstar-sextant pack --help
#northstar-sextant-pack 
#Pack Northstar containers
#
#USAGE:
#	northstar-sextant pack [OPTIONS] --manifest <MANIFEST> --root <ROOT> --out <OUT>
#
#OPTIONS:
#    -b, --block-size <BLOCK_SIZE>
#            Block size used by squashfs (default 128 KiB)
#
#    -c, --compression-algorithm <COMPRESSION_ALGORITHM>
#            Compression algorithm to use in squashfs (default gzip) [default: gzip]
#
#        --clones <CLONES>
#            Create n clones of the container
#
#    -h, --help
#            Print help information
#
#    -k, --key <KEY>
#            Key file
#
#    -m, --manifest <MANIFEST>
#            Manifest path
#
#        --mksquashfs <MKSQUASHFS>
#            mksqushfs binary [default: mksquashfs]
#
#    -o, --out <OUT>
#            Output directory
#
#    -r, --root <ROOT>
#            Container source directory
#


DEPENDS += " northstar-sextant-native"
DEPENDS += " squashfs-tools-native"

SEXTANT_PRIVATE_KEY_FILE = "${S}/examples/northstar.key"
SEXTANT_CONTAINER_MANIFEST = "${S}/examples/hello-world/manifest.yaml"
SEXTANT_CONTAINER_ROOT = "${B}/target/x86_64-poky-linux/release/hello-world"

NORTHSTAR_RUNTIME_REPOSITORY = "${D}/data/northstar/repository"

FILES:${PN} += " /data/northstar/repository/hello-world-0.0.1.npk"

do_install:append() {
	sextant_do_install
}

sextant_do_install () {
	bbnote "Executing northstar-sextent"
	bbnote "Private Key File: ${SEXTANT_PRIVATE_KEY_FILE}"
	bbnote "Container Manifest: ${SEXTANT_CONTAINER_MANIFEST}"
	bbnote "Container Root: ${SEXTANT_CONTAINER_ROOT}"
	bbnote "Output folder: ${NORTHSTAR_RUNTIME_REPOSITORY}"

	mkdir -p ${NORTHSTAR_RUNTIME_REPOSITORY}

	northstar-sextant pack \
		--key "${SEXTANT_PRIVATE_KEY_FILE}" \
		--manifest "${SEXTANT_CONTAINER_MANIFEST}" \
		--root "${SEXTANT_CONTAINER_ROOT}" \
		--out "${NORTHSTAR_RUNTIME_REPOSITORY}"
}
