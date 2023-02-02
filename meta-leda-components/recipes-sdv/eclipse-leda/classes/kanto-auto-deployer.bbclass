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

KANTO_MANIFESTS_LOCAL_DIR ??= "/var/containers/manifests"
KANTO_MANIFESTS_LOCAL_DEV_DIR ??= "/var/containers/manifests_dev"

KANTO_MANIFESTS_DIR ??= "${KANTO_MANIFESTS_LOCAL_DIR}"
KANTO_MANIFESTS_DEV_DIR ??= "${KANTO_MANIFESTS_LOCAL_DEV_DIR}"
KANTO_MANIFESTS_DIR[doc] = "The location path of Kanto Container Management deployment descriptors"
KANTO_MANIFESTS_DEV_DIR[doc] = "The location path of Kanto Container Management deployment descriptors to autodeploy"
