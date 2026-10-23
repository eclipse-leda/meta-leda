# /********************************************************************************
# * Copyright (c) 2026 Contributors to the Eclipse Foundation
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

# Build an SSAM package (.ssam) at build time with ssam-wrap and install it into
# a package directory of the ssam daemon.
#
# One recipe per package: inherit this class, set SSAM_PKG_NAME, provide a
# signing key (SSAM_PRIVATE_KEY, default: file://private-key.pem next to the
# recipe) and fill the package root in do_prepare_pkgfs().
#
# The public half of the signing key belongs to the daemon configuration, not to
# the package, and is installed by the recipe that sets up ssamd.

# erofs-* needs erofs-utils + lz4, ext4 needs e2fsprogs; both are already part
# of an image build that uses these filesystems.
DEPENDS += "ssam-wrap-native erofs-utils-native lz4-native e2fsprogs-native cryptsetup-native virtual/fakeroot-native"

# ssam-wrap runs on the build host; its -native variant comes from the ssam
# recipe in this layer (BBCLASSEXTEND = "native").
SSAM_WRAP ??= "${STAGING_DIR_NATIVE}/usr/bin/ssam-wrap"

# Defaults, which can be overridden within the recipe
SSAM_PKG_NAME ??= "${PN}"
SSAM_PKGFS_TYPE ??= "erofs-lz4hc"
SSAM_PKG_WS ??= "${WORKDIR}/ssam"

# Where the built package is installed in the image. ssamd reads packages from
# package.dir.builtin, which must exist before the daemon starts.
SSAM_PKG_DIR ??= "/data/var/lib/ssamd/builtin"

# Signing key used at build time.
SSAM_PRIVATE_KEY ??= "${UNPACKDIR}/private-key.pem"

# Optional files copied into the package root: "source:target-paths" pairs,
# source paths are relative to ${UNPACKDIR}.
SSAM_PKG_COPY ??= ""
SSAM_PKG_COPY_MODE ??= "0755"

# Optional entry point (argv) written into runtime.json.
SSAM_PKG_ARGS ??= ""

# Whether the container runs with mandatory access control (needs an AppArmor
# profile on the target).
SSAM_MAC ??= "false"

# Documentation of configuration variables
SSAM_PKG_NAME[doc] = "Name of the SSAM package and of the generated <name>.ssam file"
SSAM_PKGFS_TYPE[doc] = "Package filesystem image type: ext4, erofs, erofs-lz4 or erofs-lz4hc (default)"
SSAM_PKG_WS[doc] = "ssam-wrap workspace inside the recipe work directory"
SSAM_PKG_DIR[doc] = "Location on the rootfs where the package is installed (package.dir.builtin of ssamd)"
SSAM_PRIVATE_KEY[doc] = "RSA private key used to sign the package"
SSAM_PKG_COPY[doc] = "Files to copy into the package root, as ${UNPACKDIR}-relative 'source:target' pairs"
SSAM_PKG_COPY_MODE[doc] = "File mode used for SSAM_PKG_COPY"
SSAM_PKG_ARGS[doc] = "Entry point argv written into runtime.json, empty keeps the generated entry point"
SSAM_MAC[doc] = "Value for the 'mac' key of the generated config.toml"

# Custom tasks need an explicit sysroot dependency: openembedded-core/meta/classes-global/staging.bbclass
# only merges the native sysroot for tasks that declare one (same pattern as
# classes/sdv-container-cache.bbclass).
do_ssam_prepare[depends] += "ssam-wrap-native:do_populate_sysroot"
do_build_package[depends] += "erofs-utils-native:do_populate_sysroot lz4-native:do_populate_sysroot e2fsprogs-native:do_populate_sysroot cryptsetup-native:do_populate_sysroot virtual/fakeroot-native:do_populate_sysroot"

# ssam-wrap calls external tools by name. Native tools are not in the default
# PATH, and OE has no fakeroot: it uses pseudo (see PREFERRED_PROVIDER_virtual/fakeroot-native
# in openembedded-core/meta/conf/bitbake.conf), so provide a wrapper under the
# expected name.
ssam_pkg_env() {
    install -d ${WORKDIR}/bin
    printf '#!/bin/sh\nenv %s %s "$@"\n' "${FAKEROOTENV}" "${FAKEROOTCMD}" > ${WORKDIR}/bin/fakeroot
    chmod 0755 ${WORKDIR}/bin/fakeroot
    export PATH=${WORKDIR}/bin:${STAGING_DIR_NATIVE}/usr/bin:${PATH}
}

do_ssam_prepare() {
    ssam_pkg_env

    if [ ! -r "${SSAM_PRIVATE_KEY}" ]; then
        bbfatal "No signing key found: ${SSAM_PRIVATE_KEY}. Provide file://private-key.pem in the recipe or set SSAM_PRIVATE_KEY."
        exit 1
    fi

    bbnote "Preparing SSAM package workspace: ${SSAM_PKG_WS}"
    ${SSAM_WRAP} --prepare ${SSAM_PKG_NAME} ${SSAM_PKG_WS}
}

# Default: nothing to add. Override in the recipe to put the payload into
# ${SSAM_PKG_WS}/pkgfs.
do_prepare_pkgfs() {
    :
}

do_build_package() {
    ssam_pkg_env

    cd ${SSAM_PKG_WS}

    for entry in ${SSAM_PKG_COPY}; do
        src="${UNPACKDIR}/${entry%%:*}"
        dst="${entry##*:}"
        bbnote "Adding ${src} to the package root as /${dst}"
        install -m ${SSAM_PKG_COPY_MODE} "${src}" "pkgfs/${dst}"
    done

    if [ -n "${SSAM_PKG_ARGS}" ]; then
        bbnote "Setting package entry point to: ${SSAM_PKG_ARGS}"
        python3 -c 'import json,sys; p=sys.argv[1]; d=json.load(open(p)); d["process"]["args"]=sys.argv[2:]; json.dump(d, open(p,"w"), indent=2)' \
            runtime.json ${SSAM_PKG_ARGS}
    fi

    bbnote "Setting mandatory access control to: ${SSAM_MAC}"
    sed -i -e "s|^mac = .*|mac = ${SSAM_MAC}|" config.toml

    bbnote "Building package image: ${SSAM_PKG_NAME}.ssam (${SSAM_PKGFS_TYPE})"
    ${SSAM_WRAP} --pkgfs-type ${SSAM_PKGFS_TYPE} \
        --private-key ${SSAM_PRIVATE_KEY} \
        --output ${SSAM_PKG_WS}/${SSAM_PKG_NAME}.ssam ${SSAM_PKG_WS}

    if [ ! -s "${SSAM_PKG_WS}/${SSAM_PKG_NAME}.ssam" ]; then
        bbfatal "ssam-wrap did not produce a package: ${SSAM_PKG_WS}/${SSAM_PKG_NAME}.ssam"
        exit 1
    fi
}

do_install() {
    install -d -m 0755 ${D}${SSAM_PKG_DIR}
    install -m 0644 ${SSAM_PKG_WS}/${SSAM_PKG_NAME}.ssam ${D}${SSAM_PKG_DIR}
}

addtask do_ssam_prepare after do_unpack before do_prepare_pkgfs
addtask do_prepare_pkgfs after do_ssam_prepare before do_build_package
addtask do_build_package after do_prepare_pkgfs before do_install

FILES:${PN} += "${SSAM_PKG_DIR}/${SSAM_PKG_NAME}.ssam"

PACKAGES = "${PN}"
