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
#
# Modifies the original Kanto-CM recipe to use a custom service file that adds a 
# After=data.mount dependency for CM. This way it is ensured that CM is started AFTER
# the data partition is properly mounted and the data partition is unmounted AFTER Kanto-CM
# is fully stopped.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://service.template \
            file://config.json \
           "
SRCREV="65e00358fa5aed1792059488a80fe755b36a8191"        

do_install:append() {

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then

        # config.json
        install -d ${D}${CM_CFG_DD}/container-management
        install -m 0644 ${WORKDIR}/config.json ${D}${CM_CFG_DD}/container-management/config.json
    
        # fill in the config.json template with the custom configs provided
        sed -e 's,@CM_LOD_DD@,${CM_LOD_DD},g' \
          -e 's,@CM_THINGS_ENABLED@,${CM_THINGS_ENABLED},g' \
          -e 's,@KANTO_MANIFESTS_DIR@,${KANTO_MANIFESTS_DIR},g' \
          -i ${D}${CM_CFG_DD}/container-management/config.json

        # service.template as service
        install -d ${D}/${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/service.template ${D}${systemd_unitdir}/system/container-management.service
        # fill in the container management service template with the result configurations
        sed -e 's,@CM_BIN_DD@,${bindir},g' \
            -e 's,@CM_CFG_DD@,${sysconfdir},g' \
        -i ${D}${systemd_unitdir}/system/container-management.service
    fi
}
