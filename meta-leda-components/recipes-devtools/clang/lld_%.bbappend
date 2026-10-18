# LLD's CMake exports reference its executable as well as its libraries.
SYSROOT_DIRS:append:class-target = " ${bindir}"
