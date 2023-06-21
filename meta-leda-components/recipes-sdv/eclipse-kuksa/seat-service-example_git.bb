# /********************************************************************************
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
# ********************************************************************************/

SUMMARY = "Eclipse Kuksa - Seat Service Example"
DESCRIPTION = "This Seat Control Service Example allows moving a seat to a desired position"
HOMEPAGE = "https://github.com/eclipse/kuksa.val.services"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2b42edef8fa55315f34f2370b4715ca9"

SRC_URI = "git://github.com/eclipse/kuksa.val.services;protocol=https;branch=main \
           file://0001-Remove-conan-dependency.patch \
           file://git/CMakeLists.txt \
"

SRCREV = "07a2d28cedb1b367ae65a8e75939029f06bb24fc"
PV = "0.2.0+git${SRCPV}"

S = "${WORKDIR}/git"

inherit cmake

DEPENDS += " protobuf grpc protobuf-native grpc-native python3-cantools-native"

RDEPENDS:${PN} = "can-utils"
RDEPENDS:${PN} += " bash"

EXTRA_OECMAKE = "-DSDV_BUILD_TESTING=OFF -DCMAKE_BUILD_TYPE=Release"

# ignore "wrong place" of libcansim.so:
INSANE_SKIP:${PN} += "libdir"

# Workaround for strange cmake/build behaviour:
# The pb files are generated in a specific path, e.g.
# /build/seat_service/src/lib/grpc_services/seats_grpc_service/sdv/edge/comfort/seats/v1/seats.pb.cc
# but the compiler tries to use them from a different path:
# /build/seat_service/proto/sdv/edge/comfort/seats/v1/seats.pb.cc
#
do_configure:prepend() {
    mkdir -p "${B}/seat_service/src/lib/grpc_services/seats_grpc_service"
    ln -s "${B}/seat_service/src/lib/grpc_services/seats_grpc_service" "${B}/seat_service/proto"

    mkdir -p "${B}/seat_service/src/lib/broker_feeder"
    ln -s "${B}/seat_service/src/lib/broker_feeder" "${B}/proto"
}
