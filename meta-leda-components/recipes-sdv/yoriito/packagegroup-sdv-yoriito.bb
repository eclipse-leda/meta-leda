SUMMARY = "Yoriito packages for the SDV full image"
DESCRIPTION = "Yoriito VISS server, client, configuration, and example VSS data"
LICENSE = "Apache-2.0"

inherit packagegroup

RDEPENDS:${PN} = "packagegroup-yoriito-core"
