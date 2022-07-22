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

SUMMARY = "SDV Cloud Connector"
DESCRIPTION = "Customized fork of Eclipse Kanto azure-connector with additional support of Eclipse Backend Function Bindings and Azure C2D messages"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=b95389a3f134a33b445b438d337848f7"

SRC_URI = "git://github.com/SoftwareDefinedVehicle/sdv-self-update-agent;branch=main"
SRCREV = "c664a7f46f2bccf84acdfdda5a9af07776583eda"

S = "${WORKDIR}/git"

inherit cmake

#CFLAGS += "  "


