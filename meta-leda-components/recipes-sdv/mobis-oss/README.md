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

## Runtime requirements

`ssamd` executes workloads as OCI containers and expects an OCI runtime plus the kernel
features SSAM relies on. Partially checked in QEMU (see “Runtime test”), still not
exercised end-to-end, see the upstream README:

- `crun` (declared as `RDEPENDS` of `ssamd`, available from `meta-virtualization`) —
  present in `sdv-image-full` and reports `crun version 1.26.0` with `+SECCOMP`
- EROFS + dm-verity kernel support, loop mounts, filesystem quotas (`quotactl-rs`)
- systemd / D-Bus for the `zbus_systemd` integration

## Runtime test

See `docs/agents-integrationtesting.md` for the procedure. Running it with
`IMAGE_INSTALL:append = " ssamd ssam ssam-wrap"` on `sdv-image-full` (qemux86-64, 2026-10-22):

- all three binaries install and run on the guest (x86-64)
- `ssam-wrap --prepare <name> <workspace>` generates `config.toml`, `runtime.json`,
  `seccomp.json` and a `pkgfs/` directory
- `ssam-wrap <workspace>` needs a signing key (`-k`), it stops at “Private key file path is required”
- `ssamd` needs a configuration file: it reads `ssamd.toml` only from the directory that
  contains the binary (no CLI or environment override), so with `/usr/bin/ssamd` it looks
  for `/usr/bin/ssamd.toml`. The recipes do not ship one yet — TODO: install a default
  config there; integrators replace it with a same-named file in their own layer
- `ssam` reports “Connection refused” while no daemon runs, which is expected for the gRPC client

Follow-up work (signing keys, `ssamd.toml` defaults for the Leda `/data` layout, systemd
service, building an SSAM package) is tracked in `TODO.md` in this directory.

## Integration

Not installed by default; pull the components in explicitly, for example in a local image or
machine config:

```
IMAGE_INSTALL:append = " ssamd ssam ssam-wrap"
```

TODO for maintainers: decide whether these belong in a packagegroup. Adding them to
`packagegroup-sdv-additions` changes `sdv-image-full`, which `sdv-image-all` (the default
build target) depends on, so it would change the default image contents.

- ssamd: Daemon responsible for SSAM package management, container runtime environment configuration, and execution.
- ssam-wrap: SSAM package creation tool with Docker (and/or OCI) image conversion support.
- ssam: Command-line interface that communicates with ssamd over gRPC.

