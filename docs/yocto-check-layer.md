# Verifying the layer structure

To verify the correct structure of the metalayer, use `yocto-check-layer`:

    cd poky
    source oe-init-build-env
    yocto-check-layer --no-auto --quiet \
        ../../meta-leda-components \
        ../../meta-leda-bsp \
        ../../meta-leda-distro \
        ../../meta-rauc \
        ../../meta-virtualization \
        ../../meta-openembedded/meta-oe \
        ../../meta-openembedded/meta-filesystems/ \
        ../../meta-openembedded/meta-python/ \
        ../../meta-openembedded/meta-networking/

