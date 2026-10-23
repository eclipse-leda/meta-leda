# Installing Kas

If you are using a dev-container, kas is already installed. Otherwise use:

    pip3 install kas

# Setting up build environment using Kas

Run the following command in the root of the project to check out all dependencies and build the default image `sdv-image-all`:

    kas build

For building another supported Yocto release, use the matching `kas/.config-*.yaml`
file in the same way. Only Wrynose is supported today, see
[ci-build-matrix.md](ci-build-matrix.md) for the release and machine matrix.

To build for another machine, either use the machine specific configuration or
override the machine of a configuration:

    kas build kas/.config-wrynose-rpi4.yaml
    KAS_MACHINE=qemuarm64 kas build kas/.config-wrynose.yaml

To build a specific target, recipe or image, execute this:

    kas build --target packagegroup-sdv-core

To perform a dry-run of the build (parse recipes and resolve the dependency
tree without running any task):

    kas build kas/.config-wrynose.yaml -- --dry-run

To execute QEMU:

    kas shell -c "runqemu slirp kvm ovmf nographic" kas/.config-wrynose.yaml
