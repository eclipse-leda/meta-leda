# Continuous integration build matrix

The repository has no space on the free GitHub CI runners for full images, so
the CI matrix only runs **dry-runs**: they check out the pinned sources, parse
every recipe and resolve the full dependency tree of the image. That catches
exactly the failures that matter for this layer - missing providers, dangling
`*.bbappend`s, unresolvable dependencies - without building anything.

The matrix has two dimensions: the Yocto release and the target machine.

## Supported matrix

| Yocto release | Machine            | kas config                      | Step in `ci.yml`                       |
| ------------- | ------------------ | ------------------------------- | -------------------------------------- |
| wrynose       | `qemux86-64`       | `kas/.config-wrynose.yaml`      | `DryRun wrynose (qemux86-64)`          |
| wrynose       | `qemuarm64`        | `kas/.config-wrynose.yaml`      | `DryRun wrynose (qemuarm64)`          |
| wrynose       | `raspberrypi4-64`  | `kas/.config-wrynose-rpi4.yaml` | `DryRun wrynose (raspberrypi4-64)`   |

## One workflow, one step per machine

The whole matrix runs as a single workflow, `.github/workflows/ci.yml`,
as one job: the pre-commit hooks first, then one step per machine. The steps
run one after another on a single runner and share the build directory, so
the parse cache of the first machine is reused by the next ones. That is
faster than three workflows that each check out the sources, install the
tools and parse the whole tree from scratch, and it takes one runner slot
instead of three, which matters on the free GitHub runners.

The job stops at the first machine that does not resolve, which is the
failure a contributor has to fix first. Add `continue-on-error: true` to a
step if all machines have to be reported at once.

## Linting first, in the same job

The hooks of `.pre-commit-config.yaml` run as the first steps of the job,
over the whole tree, with the same command the contributors run locally,
see [pre-commit.md](pre-commit.md). A failing step fails the job, so the
expensive steps of the same job never start for a tree that does not even
parse: a pull request with a syntax error costs seconds instead of a
runner hour.

This is one workflow and not two. GitHub has no dependency between
workflows and none between jobs, so a second workflow could only guess when
the first one finished. Putting the cheap check in front of the expensive
steps in one job gets the order for free and costs one runner slot instead
of two.

## Keeping the setup cheap

The dry-runs spend most of their time fetching the sources and parsing them,
not resolving, and that is the part the free runners are bad at:

- The layer repositories are fetched into bare mirrors under
  `.kas-repo-ref`, the directory of `KAS_REPO_REF_DIR`, and the working
  clones of the metalayers hardlink from them: `.git/objects/info/alternates`
  of a metalayer points into the mirror directory. Fetching the pinned set
  once costs about 385 MB, re-cloning from the mirror costs seconds.
- Both the mirrors and the parse cache (`build/cache`, ~15 MB) are carried
  over between runs with `actions/cache`, keyed on the version of the
  matrix. The first run of a matrix fetches everything, every later run
  only re-clones from a mirror that was restored from the cache. Measured
  on the local machine: a dry-run that had to create the mirrors from the
  network took 1m31s, the same dry-run with the mirrors in place took 1m3s.
- The layer repositories are cloned with `KAS_CLONE_DEPTH=1`. The full
  history of the pinned sources is several hundred MB and takes minutes to
  fetch, `openembedded-core` alone is 67k commits and 259 MB, while the
  shallow clone of the same tree is a few seconds. This works because
  every pin in the kas configs is the tip of a branch or a tag; a pin to
  an older commit cannot be fetched shallow, so raise `KAS_CLONE_DEPTH`
  or drop the pin if a pin ever moves off a tip.
- The job installs nothing but `kas` and the tools that are missing on
  `ubuntu-latest`: `jq`, `shellcheck` and `openssl` for the `language:
  system` hooks, and `chrpath` and `diffstat` for the required `HOSTTOOLS`
  list of `oe-core/meta/conf/bitbake.conf`, which stops bitbake with a
  fatal error at parse time when one of those is not in `PATH`. Everything
  else of that list is on the image, so the full Yocto host list (the C/C++
  toolchains, qemu, libvirt, squashfs, rauc, skopeo, ruby) is not
  installed and a dry-run never uses it. If bitbake names more missing
  tools, add exactly those, do not install the whole host list.

What remains is parsing the ~3300 recipes and resolving the dependency
graph per machine, which is the part that cannot be avoided. If that ever
costs too much, the alternative that actually pays is running the matrix
on own hardware and reporting the result to GitHub as a check run, which
costs no runner slot at all.

## When the workflow runs

The workflow is not path scoped: it runs for every pull request and for
manual runs. The linting has to run for every pull request, including the
ones that only touch documentation, and the checks are one job, so scoping
the job would scope the linting as well. The dry-runs of a docs-only pull
request are cheap now that the parse cache and the mirrors come from the
cache of the previous run.

If that ever costs too much, the way to scope the matrix again is a
`paths` filter on the `pull_request` trigger, but then the linting of the
filtered-out pull requests has to come back as a second, cheap workflow.

## Files that affect the dependency graph

A dry-run can only fail because of a change to a file BitBake reads:

- `**/*.bb`, `**/*.bbappend`, `**/*.bbclass` - the recipes, the appends and
  the BitBake classes carried in the layers.
- `**/*.inc`, `**/*.conf`, `**/*.cfg` - the includes, the layer, distro and
  machine configuration files (`conf/layer.conf`, `conf/distro/*.conf`,
  `conf/machine/*.conf`) and the kernel and machine config fragments.
- `kas/**`, `.config*.yaml` - the kas build configurations, including the
  BitBake variables that are set in their `local_conf_header` blocks.
- `.github/workflows/**` - this workflow itself, for example when a matrix
  entry adds a kas config or switches the machine with `KAS_MACHINE`.

A change to `README.md`, to the files in `docs/`, to `.gitignore` or to
`.pre-commit-config.yaml` cannot change the dependency graph, so it cannot
make a dry-run fail. Those changes are still checked by the hooks over the
whole tree, see [pre-commit.md](pre-commit.md).

`workflow_dispatch` runs always run the whole matrix: a manual run is never
filtered, so a contributor can start a dry-run for any branch.

The workflow only listens to pull requests and to manual runs, so a direct
push to a release branch (`main`, `wrynose`, `scarthgap`, ...) runs nothing,
see [pre-commit.md](pre-commit.md).

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
3. Add one step to `.github/workflows/ci.yml`, following the steps of
   the machines that are already in the matrix. The `README.md` badge covers
   the whole workflow, there is one badge for the matrix, not one per
   machine.
