EXTRA_OECONF += "--with-wasmedge"
DEPENDS += "wasmedge"

# for compatibility for wasmedge, compile newer revisions for mickledore
SRCREV_crun = "d2ff390d37ca163ebff2467382b3a6d6c77c5ca6"
SRCREV_libocispec = "23aed835eed8d81d124977583551a81abe595a0c"
SRCREV_ispec = "4df8887994e871a59f9e30e8dd811d060f6a39ef"
SRCREV_rspec = "494a5a6aca782455c0fbfc35af8e12f04e98a55e"
SRCREV_yajl = "f344d21280c3e4094919fd318bc5ce75da91fc06"
