EXTRA_RUSTFLAGS:append = " \
    --remap-path-prefix=${WORKDIR}/sources/cargo_home/registry/src=. \
    --remap-path-prefix=${WORKDIR}/sources/target=. \
    --remap-path-prefix=${WORKDIR}=. \
"
SYSTEMD_AUTO_ENABLE:${PN} = "${@'disable' if d.getVar('LEDA_YORIITO_DEPLOYMENT') == 'container' else 'enable'}"
