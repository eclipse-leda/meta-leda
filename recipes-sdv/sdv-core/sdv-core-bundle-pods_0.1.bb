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

SUMMARY = "SDV Core Bundle Pods"
DESCRIPTION = "Make control plane to autodeploy SDV Core Bundle Pods"

SRC_URI += "file://README.txt \
            file://sdv-core-bundle-pods/ \
            file://sdv-core-bundle-pods/dapr/components \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

do_compile[noexec] = "1"

do_install() {
    # Auto-Deploy Pods: https://rancher.com/docs/k3s/latest/en/advanced/#auto-deploying-manifests
    install -d ${D}/var/lib/rancher/k3s/server/manifests
    cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/sdv-core-bundle-pods/* ${D}/var/lib/rancher/k3s/server/manifests
}

FILES_${PN} += "${datadir}/${PN}/README.txt \
                ${datadir}/${PN}/sdv-core-bundle-pods/ \
                ${datadir}/${PN}/sdv-core-bundle-pods/dapr/components \
                ${datadir}/${PN}/LICENSE"

PACKAGES = "${PN}"
