Integrating uboot targets with rauc:

To add a new rauc-uboot integration recipe, create your machine-specific .inc-file and include it in u-boot_%.bbappend.

**Important**: To make sure your integration recipe does not interfere with other targets, please use machine-specific 
overrides as much as possible e.g.:

do_install:append:<target-machine>() {
    # ...
}

DEPENDS:append:<target-machine> = " ... " 

etc.


Machine-specific files can be added in the files/<target-machine> directory. Then add to the beginning of the your .inc file:

FILESEXTRAPATHS:prepend:<target-machine> := "${THISDIR}/../files:"
SRC_URI:append:<target-machine>  = " \
    file://file_1 \
    file://file_2 \
    file://file_3 \
    ...
"

For a more detailed example, see the qemuarm64.inc recipe.