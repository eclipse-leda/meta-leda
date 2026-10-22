# Integration testing for AGENTS

Draft procedure for testing a newly added recipe at runtime. Steps marked with
`(verified)` were checked against this repository; the others still need a run.

## 1. Install the new recipe into an image

New recipes are not part of any image by default, so first pull them into the rootfs.
Create a temporary kas config (e.g. `new_recipe_install.yaml`) that only appends to
`local.conf`:

```yaml
header:
  version: 12
local_conf_header:
  integrationtesting: |
    IMAGE_INSTALL:append = " <recipe-name>"
```

`<recipe-name>` is the package name (`PN`), not the file name — e.g. `ssamd`, not
`ssamd_0.1.0.bb`. For a group of packages use the packagegroup name instead.

Check the merged configuration before building (verified: `kas dump` expands and prints
the final config without running bitbake):

```
kas dump .config.yaml:new_recipe_install.yaml
```

## 2. Build the image

```
kas build .config.yaml:new_recipe_install.yaml
```

Use the same config arguments for every step below; a different config means a
different build directory and a different image.

Pick the smallest image that is expected to carry the feature:

- `--target sdv-image-full` or `--target sdv-image-minimal` — single bootable rootfs,
  contains `openssh`, `crun`, `systemd` (verified in the rootfs manifests).
- `sdv-image-all` (the default target) is the multi-boot RAUC bundle: its wic image
  contains several rootfs partitions and u-boot picks one at boot. Use it only when the
  RAUC/multi-image layout itself is what you want to test.

The rootfs images are always re-packed, so expect a few minutes even when nothing
changed (see `AGENTS.md`).

## 3. Cheap check before booting QEMU

Confirm the package actually landed in the rootfs by looking at the manifest
(verified: `tmp/deploy/images/<machine>/<image>-<machine>.rootfs.manifest` lists
`<package> <arch> <version>` lines):

```
grep -i "<recipe-name>" build/tmp/deploy/images/qemux86-64/sdv-image-full-qemux86-64.rootfs.manifest
```

If it is missing, stop here — the runtime test cannot work.

## 4. Boot the image

`kas shell` only sets up the bitbake environment and runs the given command; it does
**not** build anything (verified: the kas `shell` plugin only sources `oe-init-build-env`
and prints a hint). So the image from step 2 must already exist.

`oe-init-build-env` puts `openembedded-core/scripts` (i.e. `runqemu`) into `PATH`
(verified), so:

```
kas shell -c "runqemu slirp kvm ovmf nographic" .config.yaml:new_recipe_install.yaml
```

Notes:

- Pass the qemuboot config explicitly if more than one image exists, otherwise runqemu
  takes the newest `*.qemuboot.conf` (verified: `ls -t` fallback in `scripts/runqemu`):

  ```
  kas shell -c "runqemu tmp/deploy/images/qemux86-64/sdv-image-full-qemux86-64.rootfs.qemuboot.conf slirp kvm ovmf nographic" .config.yaml:new_recipe_install.yaml
  ```

- `ovmf` is needed for the Leda qemux86-64 machines: they boot via EFI/u-boot
  (`EXTRA_IMAGEDEPENDS += "ovmf u-boot"` in `meta-leda-bsp/conf/machine/*.conf`).
- `kvm` needs `/dev/kvm` to be usable by the current user. On dev-containers this is
  often wrong; fix with `sudo chmod a+rwx /dev/kvm` (ownership/permissions are
  host-specific, not verified here).
- Add `-snapshot` if you do not want the run to write into the qcow2 image.
- The machine config already implies `-machine q35`, `-nographic`, serial on
  `ttyS0`/`ttyS1` and `-m 4096` (verified in the generated `qemuboot.conf`); raise
  memory with `qemuparams="-m 8192"` if a userspace test needs more.

## 5. Interacting with the guest

The console is the primary interface: runqemu uses `-serial mon:stdio`, so with
`nographic` you get the login prompt in the same terminal. Boot takes about half a
minute (kernel + systemd startup).

SSH is optional. With `slirp`, runqemu adds host forwarding
`hostfwd=tcp:127.0.0.1:2222-:22` (verified in `scripts/runqemu`), and the Leda images
set `allow-root-login` + `empty-root-password`, so:

```
ssh -p 2222 -o StrictHostKeyChecking=no root@127.0.0.1
```

(Use `-o StrictHostKeyChecking=no` only because the host key changes for ephemeral
QEMU instances.)

## 6. Smoke tests

The guest has **no package manager** (`opkg`/`rpm` are not installed) and no `ldd`,
`file` or `binutils` (verified against the `sdv-image-full` manifest). Check files
directly instead:

```
ls -l /usr/bin/<recipe-name>
head -c 20 /usr/bin/<recipe-name> | od -c | head -2   # ELF type / arch
```

Then:

- Is the binary present at the expected path, and found in `PATH`?
- Does the architecture match the machine (`x86-64` for `qemux86-64`)?
- Does it start? Run with `--help` or `--version` first, always wrapped in
  `timeout 30 ...` so an interactive or hanging program does not block the test.
- Missing shared libraries show up as a loader error
  (`error while loading shared libraries`); if you need a proper `ldd` check, add
  `binutils` to the test config's `IMAGE_INSTALL:append` and inspect on the guest,
  or inspect the binary on the host from the unpacked rootfs tarball
  (`tmp/deploy/images/<machine>/<image>.rootfs.tar.bz2`).
- For daemons: is a systemd unit shipped and does it start? Our SSAM recipes do not
  install a unit yet, so start the daemon manually under `timeout` and check its
  exit status and log output.
- For container/VM features: check the prerequisites separately (`crun` present,
  kernel features enabled) before blaming the component under test.

## 7. Shutting down

`poweroff` or `shutdown now` inside the guest exits QEMU. If it stops responding,
`pkill -f qemu-system` ends the process (this leaves the build dir untouched).
