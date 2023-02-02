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


PACKAGES = "${PN} ${PN}-dbg"
FILES:${PN} += "${bindir}/kanto-auto-deployer"
FILES:${PN} += "${systemd_unitdir}/system/kanto-auto-deployer.service"
FILES:${PN} += "${KANTO_MANIFESTS_DEV_DIR}"
FILES:${PN}-dbg += "${bindir}/.debug"

do_install:append() {
	cargo_do_install
	install_service
}

cargo_do_install () {
	local have_installed=false
	for tgt in "${B}/target/${CARGO_TARGET_SUBDIR}/"*; do
		case $tgt in
		*.so|*.rlib)
			install -d "${D}${rustlibdir}"
			install -m755 "$tgt" "${D}${rustlibdir}"
			have_installed=true
			;;
		*examples)
			if [ -d "$tgt" ]; then
				for example in "$tgt/"*; do
					if [ -f "$example" ] && [ -x "$example" ]; then
						install -d "${D}${bindir}"
						install -m755 "$example" "${D}${bindir}"
						have_installed=true
					fi
				done
			fi
			;;
		*)
			if [ -f "$tgt" ] && [ -x "$tgt" ]; then
				install -d "${D}${bindir}"
				install -m755 "$tgt" "${D}${bindir}"
				have_installed=true
			fi
			;;
		esac
	done
	if ! $have_installed; then
		die "Did not find anything to install"
	fi

}

KANTO_SERVICE_DIR = "${systemd_unitdir}/system"

install_service() {
    install -d ${D}${KANTO_SERVICE_DIR}
    install -m 0644 ${THISDIR}/files/kanto-auto-deployer.service.template ${D}${KANTO_SERVICE_DIR}/kanto-auto-deployer.service

    if [ -z ${KANTO_MANIFESTS_DEV_DIR} ]; then
        bbfatal "KANTO_MANIFESTS_DEV_DIR must not be empty, please set in your local.conf"
    fi

    # fill in the kanto auto deployer service template with the result configurations
    sed -e 's,@KD_BIN_DD@,${bindir},g' \
        -e 's,@KD_CFG_DD@,${KANTO_MANIFESTS_DEV_DIR},g' \
    -i ${D}${KANTO_SERVICE_DIR}/kanto-auto-deployer.service

    install -d ${D}${KANTO_MANIFESTS_DEV_DIR}
}


