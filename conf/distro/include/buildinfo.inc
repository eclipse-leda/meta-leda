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

# Create the /etc/build file
INHERIT += "image-buildinfo"
IMAGE_BUILDINFO_VARS:append = " DATETIME DISTRO_NAME IMAGE_BASENAME MACHINE TUNE_PKGARCH" 
IMAGE_BUILDINFO_VARS:append = " MACHINE_FEATURES DISTRO_FEATURES COMMON_FEATURES IMAGE_FEATURES"
IMAGE_BUILDINFO_VARS:append = " TUNE_FEATURES TARGET_FPU"
IMAGE_BUILDINFO_VARS:append = " APP_URI_PREFIX APP_URI_BRANCH"

