EXTRA_RUSTFLAGS:append = " \
    --remap-path-prefix=${WORKDIR}/sources/cargo_home/registry/src=. \
    --remap-path-prefix=${WORKDIR}/sources/target=. \
    --remap-path-prefix=${WORKDIR}=. \
"
