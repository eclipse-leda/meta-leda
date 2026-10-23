# Eclipse SSAM / mobis-oss

Leda GitHub Issue: https://github.com/eclipse-leda/leda/issues/82

## Eclipse SSAM

Eclipse SSAM is a container execution framework targeted for automotive ECUs.

Source URI: https://github.com/mobis-oss/ssam

Status:
- Currently onboarding to Eclipse, so repo may be moved to a new location when the transition is finalized and project got accepted.
- There are no tags, no releases, no branches yet, so we're building "git" with commitId.

The files in this meta-leda folder are recipes to build the core components of SSAM using BitBake recipes
and integrating them into the Eclipse Leda distribution.

## Recipes

- `ssamd_0.1.0.bb`: daemon (package management, container runtime configuration, execution)
- `ssam-wrap_0.1.0.bb`: package creation tool (Docker/OCI image conversion)
- `ssam_0.1.0.bb`: command line client talking to `ssamd` over gRPC
- `ssam-crates.inc`: generated crate list + checksums, `require`d by all three recipes
- `ssam-package-helloworld_1.0.0.bb`: example that builds an `.ssam` package at build
  time with `ssam-wrap` and installs it into the daemon's package directory
- `classes/ssam-package.bbclass` (in the layer root): reusable part of the above
- `files/`: `LICENSE`, example RSA key pair (`private-key.pem`, `public-key.pem`),
  `ssamd.toml` and `ssamd.service`, installed by `ssamd_0.1.0.bb` as the daemon defaults.
  Integrators replace them by shipping a file of the same name in their own layer.
  The key pair is an example only, integrators must generate their own

All three are members of one Cargo workspace in the upstream monorepo, so each recipe
checks out the same tree (`SRCREV` pinned to a commit) and builds one member via
`CARGO_SRC_DIR` + `-p <member>`. The crate list is shared because Cargo resolves the
whole workspace lockfile even when building a single member.

## Generation

Generated with `cargo-bitbake` (see `docs/new-rust-recipes.md`), then adapted:

- The upstream `Cargo.toml` already carries `repository = "https://github.com/mobis-oss/ssam"`,
  `license = "Apache-2.0"` and a committed `Cargo.lock`, so the crate list is the dependency
  closure taken from that `Cargo.lock` (workspace members `libssam`, `proc_macros`, `ssam-log`,
  `quotactl-rs` are path dependencies and ship with the checkout).
- `LIC_FILES_CHKSUM` points at the upstream `LICENSE` (Apache-2.0).

Regenerate the crate list after bumping `SRCREV`:

```
$ kas build --target ssam --cmd update_crates
```

This rewrites `ssam-crates.inc` (the `ssam` recipe is used because it owns that file name);
diff it in before bumping `SRCREV` in the three recipes.

## Pinning

Upstream has no tags or releases yet, so the recipes pin a commit:

```
SRC_URI += "git://github.com/mobis-oss/ssam;protocol=https;branch=main"
SRCREV = "559a9346032b5b69290c91aafda3dbd6055dfe57"  # main @ 2026-09-17
PV = "0.1.0"                                         # workspace Cargo.toml version
```

Bump `SRCREV` and regenerate the crate list together; `--frozen` makes Cargo fail loudly if
the vendored crates stop matching the committed `Cargo.lock`.

## Build-time requirements

- `protobuf-native`: `libssam` generates gRPC bindings with `tonic-prost-build`, which calls `protoc`.
- `clang-native` (`ssamd` only): `loopdev-3` runs `bindgen` at build time and needs libclang.
- `pkgconfig` class + `zstd`: `zstd-sys` links against the system `libzstd` through `pkg-config`;
  the class sets up the cross-compilation search paths and pulls in `pkgconf-native`.

All three recipes build with the current Leda configuration and install `usr/bin/ssam`,
`usr/bin/ssamd` and `usr/bin/ssam-wrap`.

## Package creation at build time

`classes/ssam-package.bbclass` builds an `.ssam` package with `ssam-wrap` on the build
host and installs it into a package directory of the daemon. One recipe per package:

```bitbake
inherit ssam-package
SSAM_PKG_NAME = "helloworld"
SRC_URI += "file://private-key.pem"
SSAM_PKG_ARGS = "/helloworld"          # entry point written into runtime.json
do_prepare_pkgfs() {
    # fill ${SSAM_PKG_WS}/pkgfs with the payload
}
```

Things this needed, all verified on 2026-10-22:

- `ssam-wrap_0.1.0.bb` sets `BBCLASSEXTEND = "native"` — native variants are not created
  automatically, depending on `ssam-wrap-native` alone fails with “Nothing PROVIDES”.
- custom tasks must declare their tool dependencies explicitly
  (`do_build_package[depends] += "erofs-utils-native:do_populate_sysroot …"`), otherwise
  the merged native sysroot is not created for them — see `staging_taskhandler`
  in `openembedded-core/meta/classes-global/staging.bbclass`.
- `ssam-wrap` calls `fakeroot`, which OE does not ship: OE uses pseudo
  (`PREFERRED_PROVIDER_virtual/fakeroot-native ?= "pseudo-native"`). The class writes a
  `fakeroot` wrapper running `${FAKEROOTENV} ${FAKEROOTCMD}` and depends on
  `virtual/fakeroot-native`.
- native tools are not in `PATH`; the class prepends `${STAGING_DIR_NATIVE}/usr/bin`.
- result of the example: `data/var/lib/ssamd/builtin/helloworld.ssam` (≈2 MB, `erofs-lz4hc`,
  signed, with a dm-verity table), `mac = false` in `config.toml`, entry point
  `["/helloworld"]`, payload a static x86-64 binary.

## Runtime requirements

`ssamd` executes workloads as OCI containers and expects an OCI runtime plus the kernel
features SSAM relies on. Partially checked in QEMU (see “Runtime test”), still not
exercised end-to-end, see the upstream README:

- `crun` (declared as `RDEPENDS` of `ssamd`, available from `meta-virtualization`) —
  present in `sdv-image-full` and reports `crun version 1.26.0` with `+SECCOMP`
- EROFS + dm-verity kernel support, loop mounts, filesystem quotas (`quotactl-rs`):
  `CONFIG_DM_VERITY` is enabled in the Leda kernel, `CONFIG_EROFS_FS` is not by default.
  `meta-leda-distro/recipes-kernel/linux/sdv-ssam-kernel-config.inc` adds it through the
  `ssam-erofs.cfg` config fragment, required by `linux-yocto_%.bbappend`.
- systemd / D-Bus for the `zbus_systemd` integration

## Runtime test

See `docs/agents-integrationtesting.md` for the procedure and `kas/poc-mobis-oss-ssamd.yaml`
for the config that installs these components (a proof-of-concept config, not part of the
distributed configurations):

```
kas build .config.yaml:kas/poc-mobis-oss-ssamd.yaml --target sdv-image-full
```

2026-10-22, `IMAGE_INSTALL:append = " ssamd ssam ssam-wrap"` on `sdv-image-full`
(qemux86-64): the three binaries install and run; `ssam-wrap --prepare <name> <workspace>`
generates `config.toml`, `runtime.json`, `seccomp.json` and a `pkgfs/` directory; `ssam-wrap`
stops at “Private key file path is required” without a key; `ssamd` fails because it reads
`ssamd.toml` only from the directory that contains the binary (no CLI or environment
override) and none was shipped; `ssam` reports “Connection refused”, which is expected while
no daemon runs.

2026-10-23, with the daemon configuration, the service unit and `ssam-package-helloworld`
also installed, the whole flow works on the same guest:

- `ssamd.service` is enabled and starts (`Type=notify`, `Main PID: … (/usr/bin/ssamd)`);
  it creates `downloaded`, `data` and `oci-bundle` under `package.dir` at startup
- `/usr/bin/ssamd.toml`, `/etc/ssamd/public.key` and
  `/usr/lib/systemd/system/ssamd.service` are installed
- `ssam list` reports `helloworld  Ready`: the EROFS image of the package root is mounted
  read-only through a dm-verity device on a loop device
- `ssam start helloworld` reports `[OK] helloworld`; systemd starts
  `helloworld.service` (the package's `config.toml` description is used as the unit
  description) and `journalctl --no-pager -u helloworld` shows
  `crun[…]: Hello from SSAM!` followed by `helloworld.service: Deactivated successfully`
- the package also autostarts at boot (`autostart` defaults to true in `config.toml`)

Follow-up work (see `TODO.md` in this directory): the `oci_bundle` location on the
partitioned `/data` layout, and turning the example into packages of real workloads.

## Integration

Not installed by default; pull the components in explicitly, for example in a local image or
machine config:

```
IMAGE_INSTALL:append = " ssamd ssam ssam-wrap"
```

Or use the proof-of-concept config in `kas/` (see “Runtime test” for what it produces).

TODO for maintainers: decide whether these belong in a packagegroup. Adding them to
`packagegroup-sdv-additions` changes `sdv-image-full`, which `sdv-image-all` (the default
build target) depends on, so it would change the default image contents.

- ssamd: Daemon responsible for SSAM package management, container runtime environment configuration, and execution.
- ssam-wrap: SSAM package creation tool with Docker (and/or OCI) image conversion support.
- ssam: Command-line interface that communicates with ssamd over gRPC.

