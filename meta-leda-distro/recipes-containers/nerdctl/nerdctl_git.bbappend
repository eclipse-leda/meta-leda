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
# Branch has been renamed from master to main in upstream repository

inherit go

SRC_URI:remove = "git://github.com/containerd/nerdctl.git;name=nerdcli;branch=master;protocol=https"

# Appending will not work, as the main SRC_URI checkout will clear the target directory and then
# the vendor.fetch folder will be missing. Hence, prepending the new url to the list of SRC_URIs.
SRC_URI =+ "git://github.com/containerd/nerdctl.git;name=nerdcli;branch=main;protocol=https"