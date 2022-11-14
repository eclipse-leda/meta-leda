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
# Eclipse Leda

SRC_URI:append := " file://northstar.toml"
SRC_URI:append := " file://northstar.service"

FILES:${PN} += " /etc/northstar/northstar.toml \
				/etc/northstar/examples/northstar.pub \
                ${systemd_system_unitdir}/northstar.service"

inherit systemd features_check

NATIVE_SYSTEMD_SUPPORT = "1"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "northstar.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
REQUIRED_DISTRO_FEATURES = "systemd"

# S is ./git for 
do_install:append() {
	# Installing the config file from Leda (not the one from the origin sources)
    install -D -m 0644 "${S}/../northstar.toml" "${D}/etc/northstar/northstar.toml"

	install -D -m 0644 "${S}/examples/northstar.pub" "${D}/etc/northstar/examples/northstar.pub"

	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
			install -D -m 0644 "${S}/../northstar.service" "${D}${systemd_system_unitdir}/northstar.service"
	fi
}

do_install:append() {
	cargo_do_install
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
