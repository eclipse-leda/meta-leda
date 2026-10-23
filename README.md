[![Linting](https://img.shields.io/github/actions/workflow/status/eclipse-leda/meta-leda/linting.yml)](https://github.com/eclipse-leda/meta-leda/actions/workflows/linting.yml) [![BitBake DryRun](https://img.shields.io/github/actions/workflow/status/eclipse-leda/meta-leda/dryrun.yml)](https://github.com/eclipse-leda/meta-leda/actions/workflows/dryrun.yml)

# OpenEmbedded Meta-Layer for Software Defined Vehicles components

This metalayer can be used to integrate software-defined vehicle open source components into a Yocto based build.

It is based on the guidelines documented in the [Yocto Project Documentation](https://docs.yoctoproject.org/3.1.13/brief-yoctoprojectqs/brief-yoctoprojectqs.html#creating-your-own-general-layer)

# Structure

- Eclipse Leda Quickstart Distribution: `meta-leda/meta-leda-distro`
- BSP for QEMU and Raspberry Pi: `meta-leda/meta-leda-bsp`
- SDV Component Recipes: `meta-leda/meta-leda-components`

Note: `meta-leda` is a [container layer](https://docs.yoctoproject.org/ref-manual/terms.html#term-Container-Layer).

# Usage

After cloning the repository, add the needed layers to your build configuration, for example:

    bitbake-layers add-layer meta-leda/meta-leda-components

Enable the `sdv` distro feature by adding the following line to your distro (or local) configuration:

    DISTRO_FEATURES:append = " sdv"

## Building using kas

The repository contains an example configuration (`.config.yaml`) for [kas](https://github.com/siemens/kas) . To build the image, simply run the kas build command:

    kas build

## Supported Yocto releases

The images in this repository are built against **Wrynose**, the current
development release of Yocto. Earlier releases are end of life and are not
supported by this layer any more, see
[docs/ci-build-matrix.md](docs/ci-build-matrix.md).

# Dependencies

The sources pinned by `kas/.config-wrynose.yaml` are:

- BitBake
  - URI: https://github.com/openembedded/bitbake.git
  - Tag: yocto-6.0.3
- poky
  - URI: https://git.yoctoproject.org/meta-yocto/
  - Branch: wrynose
  - Layers:
    - meta-poky
    - meta-yocto-bsp
- openembedded-core
  - URI: https://github.com/openembedded/openembedded-core.git
  - Tag: yocto-6.0.3
  - Layers:
    - meta
- meta-openembedded
  - URI: https://github.com/openembedded/meta-openembedded.git
  - Branch: wrynose
  - Layers:
    - meta-oe
    - meta-filesystems
    - meta-python
    - meta-networking
- meta-clang
  - URI: https://github.com/kraj/meta-clang.git
  - Branch: wrynose
- meta-virtualization
  - URI: https://git.yoctoproject.org/meta-virtualization
  - Branch: wrynose
- meta-rauc
  - URI: https://github.com/rauc/meta-rauc.git
  - Branch: wrynose
- meta-rauc-community
  - URI: https://github.com/rauc/meta-rauc-community.git
  - Branch: master
  - Layers:
    - meta-rauc-qemux86
- meta-kanto
  - URI: https://github.com/mikehaller/meta-kanto.git
  - Branch: wrynose
  - Commit: 171b7da7942dd7d18ef45076a99c37f838c47ed1
  - Note: temporary fork until https://github.com/eclipse-kanto/meta-kanto supports wrynose
- meta-lts-mixins, only for images that use Rust or Go content
  - URI: https://git.yoctoproject.org/git/meta-lts-mixins
  - Branch: wrynose/rust and wrynose/go

`kas/.config-wrynose-rpi4.yaml` adds `meta-raspberrypi` and the
`meta-rauc-raspberrypi` layer for the `raspberrypi4-64` machine.

# License and Copyright

## Copyright

All content is the property of the respective authors or their employers. For
more information regarding authorship of content, please consult the listed
source code repository logs.

## Declared Project Licenses

This program and the accompanying materials are made available under the
terms of the Apache License 2.0 which is available at
https://www.apache.org/licenses/LICENSE-2.0

## Source Code

The project maintains the following source code repositories:

- https://github.com/eclipse-leda/meta-leda

## Cryptography

Content may contain encryption software. The country in which you are currently
may have restrictions on the import, possession, and use, and/or re-export to
another country, of encryption software. BEFORE using any encryption software,
please check the country's laws, regulations and policies concerning the import,
possession, or use, and re-export of encryption software, to see if this is
permitted.
