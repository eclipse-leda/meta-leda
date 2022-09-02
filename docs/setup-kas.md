# Installing Kas
If you are using a dev-contaier, kas is already installed. Otherwise use:

    pip3 install kas

# Setting up build environment using Kas

Run the following command in the root of the project to check out all dependencies and build the default image `sdv-image-all`:

    kas build

For building other Yocto releases, different `kas/.config-*.yaml` files can be used in same way:

    kas build kas/.config-master.yaml

To build a specific target, recipe or image, execute this:

    kas build --target packagegroup-sdv-core

