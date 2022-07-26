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

SUMMARY = "SDV: Vehicle Data Broker CLI"
DESCRIPTION = "A command line tool for interacting with the Vehicle Data Broker"

do_fetch() {
    GH_LOC=$(PATH=/usr/bin:${PATH} whereis gh)
    bbnote "GitHub CLI Location: ${GH_LOC}"
    PATH=/usr/bin:${PATH}
   
    gh release download v0.17.0 \
        --archive=zip \
        --repo eclipse-velocitas/vehicleapi \
        --dir ${DL_DIR} || true
    
    gh release download v0.17.0 \
        --pattern 'bin_release_databroker_*.tar.gz' \
        --repo eclipse-velocitas/vehicleapi \
        --dir ${DL_DIR} || true
}

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/leda-incubator-vehicleapi-0.17.0/LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

INSANE_SKIP:${PN} = "already-stripped"

do_install () {
    install -d ${D}${bindir}
    case ${TARGET_ARCH} in
        aarch64*)    
            install -m 0755 ${WORKDIR}/target/aarch64-unknown-linux-gnu/release/vehicle-data-cli ${D}${bindir}
            ;;
        x86_64*)
            install -m 0755 ${WORKDIR}/target/release/vehicle-data-cli ${D}${bindir}
            ;;
        *)
            bbfatal "Recipe has no target-arch-to-vapi-release-arch mapping for '${TARGET_ARCH}'."
            ;;
    esac

}

FILES:${PN} = "${bindir}/vehicle-data-cli"