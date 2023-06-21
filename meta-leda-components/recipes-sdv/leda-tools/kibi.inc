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

do_install:append() {
    install_syntax_highlighting_confs
    alias_nano
}

install_syntax_highlighting_confs() {
    install -d ${D}${datadir}/kibi
    cp -r ${S}/syntax.d ${D}${datadir}/kibi
}

alias_nano() {
    if [ ${KIBI_ALIAS_NANO} -ne 0 ]; then
        ln --relative --symbolic ${D}${bindir}/kibi ${D}${bindir}/nano
    fi
}
