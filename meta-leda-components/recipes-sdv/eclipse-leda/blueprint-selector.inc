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
#

BLUEPRINTS_DIR ??= "/var/containers/blueprints"

DEPENDS += " pkgconfig-native openssl"

cargo_do_compile:prepend() {
    export PKG_CONFIG_ALLOW_CROSS=1
    export DEFAULT_BLUEPRINTS_DIR="${BLUEPRINTS_DIR}"
}
