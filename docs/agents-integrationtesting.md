# Integration testing for AGENTS

Procedure for testing a newly added recipe at runtime. Notes in parentheses say where
a claim was checked against this repository. The whole procedure was run end to end on
2026-10-22 with the Eclipse SSAM recipes (`ssamd`, `ssam`, `ssam-wrap`); see the last
section for what was observed.

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

The autologin prompt says `Type 'root' to login with superuser privileges (no
password will be asked)` (observed), so the console needs one line of input: `root`.

SSH is possible but not free: the forward exists (`hostfwd=tcp:127.0.0.1:2222-:22`,
verified in `scripts/runqemu`), yet `sshd` is not running at boot, so a plain `ssh`
attempt times out during the banner exchange (observed). Start the daemon first
(`systemctl start sshd`) if you want the ssh route; otherwise stay on the console.

## 6. Smoke tests

The guest has **no package manager** (`opkg`/`rpm` are not installed) and no `ldd`,
`file` or `binutils` (verified against the `sdv-image-full` manifest). It is a BusyBox
system with `zsh` as the login shell on `sdv-image-full`, so:

- `head` has no `-c` option (BusyBox), and `head -20` is rejected as an invalid option.
- there is **no `timeout`** command, and `busybox timeout` reports “applet not found”.
- `ls`, `od`, `find`, `mkdir`, `strace`, `systemctl` are available.

Check files directly:

```
ls -l /usr/bin/<recipe-name>
od -N 16 /usr/bin/<recipe-name> | head -n 2   # ELF type / arch
uname -a                                      # guest arch
```

Then:

- Is the binary present at the expected path, and found in `PATH`?
- Does the architecture match the machine (`x86-64` for `qemux86-64`)? Running the
  binary proves it; a mismatch shows up as `exec format error` or a loader error.
- Does it start? Run with `--help` or `--version` first. Since there is no `timeout`
  in the guest, bound hanging programs from the host instead: after sending the
  commands, kill QEMU with `pkill -f "[q]emu-system-x86_64 -device"` (the bracket
  avoids matching the calling shell).
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

`poweroff` inside the guest stops systemd, QEMU exits and `runqemu` prints
`Cleaning up` (observed). If it stops responding, `pkill -f "[q]emu-system-x86_64 -device"`
ends the process; this leaves the build directory untouched.

## 8. Feeding commands non-interactively

`runqemu` reads the guest console from its own stdin (`-serial mon:stdio`), so a
scripted test is possible. Two gotchas found while running this:

- Piping the whole command list from the start does not work: the input is consumed
  during boot, and getty discards pending input after the login line, so every
  command after `root` is dropped.
- Send the login first, then the commands, with a delay that covers the boot:

```sh
# /tmp/qemu-run.sh — run with: bash /tmp/qemu-run.sh > /tmp/runqemu.log 2>&1 &
cd /workspaces/meta-leda || exit 1
( source openembedded-core/oe-init-build-env build >/dev/null 2>&1
  runqemu tmp/deploy/images/qemux86-64/sdv-image-full-qemux86-64.rootfs.qemuboot.conf slirp kvm ovmf nographic ) \
  < <(sleep 100; printf 'root\n'; sleep 20; cat /tmp/guest-commands.txt)
```

The first `sleep` covers boot (about 40 s for `sdv-image-full` with a warm cache), the
second one is the gap between login and the shell being ready. Read the log afterwards
and strip the console escapes, e.g. `cat -v` plus a `sed` for the ANSI sequences.

## 9. Observed with the SSAM recipes (2026-10-22)

With `IMAGE_INSTALL:append = " ssamd ssam ssam-wrap"` on `sdv-image-full`:

- all three appear in the rootfs manifest and install at `/usr/bin/ssam`,
  `/usr/bin/ssam-wrap`, `/usr/bin/ssamd` (2.6 MB / 5.2 MB / 15.2 MB — not stripped,
  see `docs/new-rust-recipes.md`)
- `crun` is present (`crun version 1.26.0`, `+SYSTEMD +SELINUX +APPARMOR +CAP +SECCOMP +EBPF`)
- `ssam-wrap --prepare <name> <workspace>` works and writes `config.toml`,
  `runtime.json`, `seccomp.json` and a `pkgfs/` directory
- `ssam-wrap <workspace>` stops at “Private key file path is required”: package
  creation needs a signing key (`-k`), so the test has to provide one
- `ssamd` stops at “Failed to load daemon configuration … /usr/bin/ssamd.toml”: no
  config file is shipped by the recipe, and the daemon looks next to the binary. Needs
  a default config in the package before the daemon can be started
- `ssam` runs and fails with “Connection refused” while no daemon is running, which is
  the expected behaviour for the gRPC client

## 10. Observed with the SSAM recipes (2026-10-23)

Same procedure, with `ssam-package-helloworld` added to `IMAGE_INSTALL`, the default
`ssamd.toml` + `ssamd.service` shipped by `ssamd_0.1.0.bb`, and `CONFIG_EROFS_FS=y`
enabled through `meta-leda-distro/recipes-kernel/linux/sdv-ssam-kernel-config.inc`
(`CONFIG_EROFS_FS` is not set in the Leda kernel by default, and mounting an `ext4`
pkgfs fails with `Failed to load table: … Argument list too long (os error 7)`):

- `systemctl status ssamd`: `loaded … enabled; preset: enabled`, `active (running)`,
  `Main PID: … (ssamd)`; the daemon creates `downloaded`, `data` and `oci-bundle`
  under its package directories at startup, `builtin` comes from the image
- `ssam list` prints `helloworld  Ready`
- `ssam start helloworld` prints `Starting package(s): helloworld` / `[OK] helloworld`
- `journalctl --no-pager -u helloworld` shows
  `systemd[1]: Started A short description of the package.` (the unit description comes
  from the package `config.toml`), `crun[…]: Hello from SSAM!`,
  `helloworld.service: Deactivated successfully` — the package container runs through
  `crun` and exits, as `service_type = simple` means
- the package also autostarts at boot: `autostart` defaults to true in `config.toml`
- `ssam --help` lists `start`, `stop`, `list`, `install`, `remove`, `wipe-data`,
  `timeline-info`, `pkg-info`
