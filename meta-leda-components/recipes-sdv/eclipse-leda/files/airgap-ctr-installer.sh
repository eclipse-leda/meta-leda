#!/bin/sh
# shellcheck disable=SC3043
# shellcheck disable=SC1091
# shellcheck disable=SC2086
# shellcheck disable=SC2034
# shellcheck disable=SC2059
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


for tar_image in $1
    ctr --namespace kanto-cm image import ${tar_image}
done