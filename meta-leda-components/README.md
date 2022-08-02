# OpenEmbedded Meta-Layer for Software Defined Vehicles components

This metalayer can be used to integrate software-defined vehicle open source components into a Yocto based build.

It is based on the guidelines documented in the [Yocto Project Documentation](https://docs.yoctoproject.org/3.1.13/brief-yoctoprojectqs/brief-yoctoprojectqs.html#creating-your-own-general-layer)

# Structure

- Eclipse Leda Quickstart Distribution: `meta-leda/meta-sdv-distro`
- BSP for QEMU and Raspberry Pi: `meta-leda/meta-sdv-bsp`
- SDV Component Recipes: `meta-leda/meta-sdv-components`

# Dependencies

This layer depends on
- poky
- meta-virtualization
- meta-rauc
- meta-openembedded
- meta-security
- meta-rauc-community

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

* https://github.com/eclipse-leda/

## Cryptography

Content may contain encryption software. The country in which you are currently
may have restrictions on the import, possession, and use, and/or re-export to
another country, of encryption software. BEFORE using any encryption software,
please check the country's laws, regulations and policies concerning the import,
possession, or use, and re-export of encryption software, to see if this is
permitted.
