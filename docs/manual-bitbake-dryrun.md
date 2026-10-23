# Manual BitBake Install and DryRun

After creating the DevContainer, set up the build environment by checking out Poky and the required layers

*Note: This setup is much easier to do with the `kas` tooling. See [Setup with kas](setup-kas.md)*

Clone Poky and switch to the branch under test:

    git clone git://git.yoctoproject.org/meta-poky poky
    cd poky
    git checkout -t origin/wrynose -b wrynose

Pull updates if necessary:

    git config pull.rebase false
    git pull

Set up build environment:

    source oe-init-build-env

Dry-run a build of the Linux Kernel recipe using BitBake:

    bitbake --dry-run linux-yocto

Check out dependencies for the meta-leda metalayer:

    cd ${CODESPACE_VSCODE_FOLDER}
    git clone -b wrynose https://github.com/rauc/meta-rauc.git meta-rauc
    git clone -b master https://github.com/rauc/meta-rauc-community.git meta-rauc-community
    git clone -b wrynose https://git.yoctoproject.org/meta-virtualization meta-virtualization
    git clone -b wrynose https://git.openembedded.org/meta-openembedded meta-openembedded

From the `poky/build` directory, add the meta-leda Layer to the BBLAYERS configuration:

    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-rauc
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-rauc-community/meta-rauc-qemux86
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-openembedded/meta-oe
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-openembedded/meta-filesystems
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-openembedded/meta-python
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-openembedded/meta-networking
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-virtualization
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-leda-components
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-leda-bsp
    bitbake-layers add-layer ${CODESPACE_VSCODE_FOLDER}/meta-leda-distro

Dry-run a build of one of the Leda recipes:

    DISTRO=leda bitbake --dry-run sdv-image-all

Perform a real build of one of the targets, e.g. the Eclipse Kanto Container Management recipe:

    DISTRO=leda bitbake container-management

To build a specific machine, use the machine specific configuration or set the
KAS_MACHINE environment variable:

    kas build kas/.config-wrynose-rpi4.yaml --target sdv-raspberry-growdisk
    KAS_MACHINE=qemuarm64 kas build kas/.config-wrynose.yaml --target sdv-image-all
