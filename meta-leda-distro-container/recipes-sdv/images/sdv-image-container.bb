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

SUMMARY = "An image which serves as parent image for OCI containers"

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
       ${CONTAINER_SHELL} \
"

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

# create stable symlinks to the latest container image
IMAGE_CMD:append:oci() {
    cd ${IMGDEPLOYDIR}

    if [ -n "${OCI_IMAGE_TAR_OUTPUT}" ]; then

        cd $image_name
        tar -cf "../$image_name.tar" *
        cd -

	    ln -sf "$image_name.tar" "${IMAGE_BASENAME}-${MACHINE}-oci.tar"
    fi

    ln -sf $image_name ${IMAGE_BASENAME}-${MACHINE}-oci
}
