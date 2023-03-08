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


PACKAGES = "${PN} ${PN}-dbg"
FILES:${PN} += "${bindir}/kibi"
FILES:${PN}-dbg += "${bindir}/.debug"

do_install:append() {
	cargo_do_install
	install_syntax_highlighting_confs
	alias_nano
}

cargo_do_install () {
	install_syntax_highlighting_confs
	local have_installed=false
	for tgt in "${B}/target/${CARGO_TARGET_SUBDIR}/"*; do
		case $tgt in
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

install_syntax_highlighting_confs() {
	if [ ${KIBI_SYNTAX_HIGHLIGHTING} -ne 0 ]; then
		install -d ${D}${datadir}/kibi
		cp -r ${S_SYNTAX}/syntax.d ${D}${datadir}/kibi
	fi
}	

alias_nano() {
	if [ ${KIBI_ALIAS_NANO} -ne 0 ]; then
		ln --relative --symbolic ${D}${bindir}/kibi ${D}${bindir}/nano
	fi
}