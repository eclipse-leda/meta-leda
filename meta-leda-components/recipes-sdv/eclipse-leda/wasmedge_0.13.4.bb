DESCRIPTION = "WasmEdge is a lightweight, high-performance, and extensible WebAssembly runtime."

SRC_URI += "file://LICENSE"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

S = "${WORKDIR}"

inherit cmake

SRC_URI += "gitsm://github.com/WasmEdge/WasmEdge.git;protocol=https;nobranch=1;branch=main"
SRCREV = "acd72bc07e02bd87f01f547f28700f2557eb5ba9"
S = "${WORKDIR}/git"

DEPENDS += " llvm boost spdlog clang"

EXTRA_OECMAKE = "-DCMAKE_BUILD_TYPE=Release"
