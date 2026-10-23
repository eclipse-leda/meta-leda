# Continuous integration build matrix

The repository has no space on the free GitHub CI runners for full images, so
the CI matrix only runs **dry-runs**: they check out the pinned sources, parse
every recipe and resolve the full dependency tree of the image. That catches
exactly the failures that matter for this layer - missing providers, dangling
`*.bbappend`s, unresolvable dependencies - without building anything.

The matrix has two dimensions: the Yocto release and the target machine.

## Supported matrix

| Yocto release | Machine            | kas config                      | Step in `dryrun.yml`                   |
| ------------- | ------------------ | ------------------------------- | -------------------------------------- |
| wrynose       | `qemux86-64`       | `kas/.config-wrynose.yaml`      | `DryRun wrynose (qemux86-64)`          |
| wrynose       | `qemuarm64`        | `kas/.config-wrynose.yaml`      | `DryRun wrynose (qemuarm64)`          |
| wrynose       | `raspberrypi4-64`  | `kas/.config-wrynose-rpi4.yaml` | `DryRun wrynose (raspberrypi4-64)`   |

## One workflow, one step per machine

The whole matrix runs as a single workflow, `.github/workflows/dryrun.yml`,
as one job with one step per machine. The steps run one after another on a
single runner and share the kas build directory, so the parse cache of the
first machine is reused by the next ones. That is faster than three
workflows that each check out the sources, install the tools and parse the
whole tree from scratch, and it takes one runner slot instead of three,
which matters on the free GitHub runners.

The job stops at the first machine that does not resolve, which is the
failure a contributor has to fix first. Add `continue-on-error: true` to a
step if all machines have to be reported at once.

## When the dry-runs run

The dry-runs are the expensive part of CI, so the workflow is scoped with a
`paths` filter on the `pull_request` trigger. It only runs when a file that
BitBake reads has changed:

- `**/*.bb`, `**/*.bbappend`, `**/*.bbclass` - the recipes, the appends and
  the BitBake classes carried in the layers.
- `**/*.inc`, `**/*.conf`, `**/*.cfg` - the includes, the layer, distro and
  machine configuration files (`conf/layer.conf`, `conf/distro/*.conf`,
  `conf/machine/*.conf`) and the kernel and machine config fragments.
- `kas/**`, `.config*.yaml` - the kas build configurations, including the
  BitBake variables that are set in their `local_conf_header` and
  `bblayers_conf_header` blocks.
- `.github/workflows/**` - this workflow itself, for example when a matrix
  entry adds a kas config or switches the machine with `KAS_MACHINE`.

A change to `README.md`, to the files in `docs/`, to `.gitignore` or to
`.pre-commit-config.yaml` does not affect the dependency graph, so it does
not start the dry-runs. Those changes are still checked by the `Linting`
job, which runs the repository hooks over the whole tree, see
[pre-commit.md](pre-commit.md).

`workflow_dispatch` runs are never filtered: a manual run always runs, so a
contributor can start a dry-run for any branch.

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
  matrix entry and has no step in the workflow.

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
3. Add one step to `.github/workflows/dryrun.yml`, following the steps of
   the machines that are already in the matrix. The `README.md` badge covers
   the whole workflow, there is one badge for the matrix, not one per
   machine.
