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

# Version of tmux is 2.1
LIC_FILES_CHKSUM = "file://tmux.c;beginline=3;endline=17;md5=8685b4455330a940fab1ff451aa941a0"

SRC_URI = "git://github.com/tmux/tmux.git;protocol=https;branch=master"
SRCREV = "310f0a960ca64fa3809545badc629c0c166c6cd2"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"
