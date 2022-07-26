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

SUMMARY = "An SDK image which serves as parent image for toolchains for building OCI containers"

IMAGE_FSTYPES = "container oci"
inherit image
inherit image-oci

IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""
NO_RECOMMENDATIONS = "1"

IMAGE_INSTALL = " \
       base-files \
       base-passwd \
       netbase \
       git \
       ca-certificates \
       packagegroup-sdk-target \
       cmake \
       file \
       python3 \
       python3-pip \
       python3-setuptools \
       python3-wheel \
       meson \
       strace \
       ${CONTAINER_SHELL} \
"

#
# To be checked:
#   binutils  -> binutils-symlinks ?!?
#   cmake
#   file
#   python3?
#   ...


# RUN apt-get update && apt-get -y install \ 
#    autoconf binutils cmake file \
#    gcc g++ git libtool make \
#    build-essential libcurl4-openssl-dev \
#    binutils-aarch64-linux-gnu gcc-9-aarch64-linux-gnu g++-9-aarch64-linux-gnu \
#    python3 python3-pip python3-setuptools python3-wheel ninja-build meson \
#    libselinux1-dev libmount-dev libmount1 libblkid-dev


# If the following is configured in local.conf (or the distro):
#      PACKAGE_EXTRA_ARCHS:append = " container-dummy-provides"
# 
# it has been explicitly # indicated that we don't want or need a shell, so we'll
# add the dummy provides.
# 
# This is required, since there are postinstall scripts in base-files and base-passwd
# that reference /bin/sh and we'll get a rootfs error if there's no shell or no dummy
# provider.
CONTAINER_SHELL ?= "${@bb.utils.contains('PACKAGE_EXTRA_ARCHS', 'container-dummy-provides', 'container-dummy-provides', 'busybox', d)}"

# Allow build with or without a specific kernel
IMAGE_CONTAINER_NO_DUMMY = "1"

# Workaround /var/volatile for now
ROOTFS_POSTPROCESS_COMMAND += "rootfs_fixup_var_volatile ; "
rootfs_fixup_var_volatile () {
    install -m 1777 -d ${IMAGE_ROOTFS}/${localstatedir}/volatile/tmp
    install -m 755 -d ${IMAGE_ROOTFS}/${localstatedir}/volatile/log
}

