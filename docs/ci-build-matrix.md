# Continuous integration build matrix

The repository has no space on the free GitHub CI runners for full images, so
the CI matrix only runs **dry-runs**: they check out the pinned sources, parse
every recipe and resolve the full dependency tree of the image. That catches
exactly the failures that matter for this layer - missing providers, dangling
`*.bbappend`s, unresolvable dependencies - without building anything.

The matrix has two dimensions: the Yocto release and the target machine.

## Supported matrix

| Yocto release | Machine            | kas config                     | GitHub workflow              |
| ------------- | ------------------ | ------------------------------ | ---------------------------- |
| wrynose       | `qemux86-64`       | `kas/.config-wrynose.yaml`     | `dryrun-qemux86-64.yml`      |
| wrynose       | `qemuarm64`        | `kas/.config-wrynose.yaml`     | `dryrun-qemuarm64.yml`       |
| wrynose       | `raspberrypi4-64`  | `kas/.config-wrynose-rpi4.yaml`| `dryrun-raspberrypi4-64.yml` |

## Naming convention for kas configs

- `kas/.config-<release>.yaml` - one config per supported Yocto release,
  default machine `qemux86-64`, target `sdv-image-all`.
- `kas/.config-<release>-<machine>.yaml` - only for machines that need extra
  layers, for example `raspberrypi4-64`, which adds `meta-raspberrypi` and the
  `meta-rauc-raspberrypi` layer from `meta-rauc-community`.
- Qemu machines need no extra layers, so they reuse the release config and
  switch machine with `KAS_MACHINE=<machine>` in the workflow environment.
- `kas/.config-components.yaml` - packagegroups only, used by the examples and
  by contributors who want the SDV content without a Leda distro. It is not a
  matrix entry and has no workflow.

## Why scarthgap and kirkstone are not in the matrix

Both were checked in October 2026 and do not resolve, so support was dropped and
`LAYERSERIES_COMPAT_*` in the layer.conf files only lists `wrynose`:

- `packagegroup-sdv-core` pulls `crun`, which depends on `wasmedge`, which
  `DEPENDS on lld`. `lld` only exists in oe-core for wrynose; neither oe-core
  scarthgap nor meta-clang scarthgap provide it, so the dependency chain has no
  buildable provider.
- `meta-virtualization` for scarthgap has no `containerd` recipe, so the
  `containerd_%.bbappend` in `meta-leda-distro` is left dangling. The same
  applies to the `lld_%.bbappend` in `meta-leda-components`.

## Extending the matrix

To add a release or a machine:

1. Add or adapt the kas config and run the dry-run locally:

   ```sh
   kas build kas/.config-<release>.yaml -- --dry-run
   ```

2. Only add it to the matrix if the dry-run is clean. `BB_DANGLINGAPPENDS_WARNONLY`
   can turn the dangling-append error into a warning, but the dependency errors
   still have to be resolved.
3. Add one workflow per machine, following `dryrun-qemux86-64.yml`, and add the
   badge to `README.md`.
