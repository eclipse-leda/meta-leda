# ********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************

SUMMARY = "Eclipse Kuksa - GPS Feeder"
DESCRIPTION = "GPS data source for KUKSA.val Server and KUKSA.val Databroker"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=175792518e4ac015ab6696d16c4f607e"

SRC_URI = "git://github.com/eclipse/kuksa.val.feeders.git;protocol=https;branch=main \
           file://gpsd_feeder.ini \
"

PV = "1.0+git${SRCPV}"
SRCREV = "1047b5230b5bf80c3794252288a9b8622a6b70d2"

S = "${WORKDIR}/git/gps2val"

RDEPENDS:${PN} = " \
    python3-kuksa-client (>= 0.4a4) \
    python3-gpsdclient \
"

FILES:${PN} = "\
    ${bindir}/gpsd_feeder \
    ${sysconfdir}/gpsd_feeder.ini \
"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${S}/gpsd_feeder.py ${D}${bindir}/gpsd_feeder
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/gpsd_feeder.ini ${D}${sysconfdir}/gpsd_feeder.ini
}
