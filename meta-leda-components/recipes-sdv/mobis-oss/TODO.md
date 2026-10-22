# Next steps for the SSAM integration

Draft of the remaining work, checked against upstream docs and the runtime test run on
2026-10-22 (see `docs/agents-integrationtesting.md`). Items marked **open** still need
a decision or a run.

Upstream references (paths inside `https://github.com/mobis-oss/ssam`):

- `README.md` (Quick start, steps 1–5)
- `docs/guides/serving-ssam.md` (daemon setup, systemd, `gen_ssamd_conf.sh`)
- `docs/reference/daemon-config.md` (`ssamd.toml` keys and defaults)
- `docs/reference/ssam-wrap.md`, `docs/reference/ssam.md` (CLI references)
- `ssamd/releasedata/gen_ssamd_conf.sh` (config + service unit generator)

## 1. Example signing key pair

Every package is signed with an RSA private key; `ssamd` verifies it with the public
half at `package.public_key` (default `/etc/ssamd/public.key`). Upstream generates:

```
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -in private_key.pem -pubout -out public_key.pem
```

- Put both files in `examples/ssam/` with a short README saying they are an **example
  only** — integrators must generate their own pair.
- Upstream warns that the public key is the whole trust model: keep it on a read-only
  / secure-boot protected path and restrict writes. `/etc/ssamd/public.key` is fine for
  a first test, but flag it for review.
- `openssl` is present in `sdv-image-full` (3.5.7), so keys can also be generated in
  the guest; generating them on the host and installing them is simpler and
  reproducible.

## 2. systemd integration of `ssamd`

Constraints verified in the runtime test and in `docs/reference/daemon-config.md`:

- `ssamd` reads `ssamd.toml` **only from the directory that contains the binary**, and
  there is no CLI or environment override. With the binary at `/usr/bin/ssamd`, the
  config must be at `/usr/bin/ssamd.toml`.
  **Decision:** ship the default config as a file in this recipe directory
  (`files/ssamd.toml`) and install it next to the binary. Integrators override it the
  standard BitBake way — their own layer contains a same-named recipe/file earlier in
  the layer search path — so no bbappend, no symlink indirection.
- `ssamd` must run as root and signals readiness with `sd_notify`; upstream recommends a
  `Type=notify` unit. `gen_ssamd_conf.sh --gen-service-unit` generates a matching
  `ssamd.service` (and `--gen-apparmor-profile` generates the AppArmor profiles), so
  generating both at build time is preferable to hand-writing them.
  The script needs `bash` and lives at `ssamd/releasedata/gen_ssamd_conf.sh` in the
  checkout; its path flags (`--builtin-pkgs-dir`, `--downloaded-pkgs-dir`,
  `--pkgs-data-root`, `--oci-bundle-dir`, `--public-key-file`, `--ssamd-install-path`)
  map to these TOML keys:

| TOML key | upstream example value | proposed meta-leda value |
| --- | --- | --- |
| `package.dir.builtin` | `/var/lib/ssamd/builtin` (config reference shows `/usr/share/ssamd/packages`) | `/data/var/lib/ssamd/builtin` |
| `package.dir.downloaded` | `/var/lib/ssamd/downloaded` (config reference: `/var/lib/ssamd/packages`) | `/data/var/lib/ssamd/downloaded` |
| `package.dir.data` | `/var/lib/ssamd/data` | `/data/var/lib/ssamd/data` |
| `package.dir.oci_bundle` | `/var/run/ssamd/oci-bundle` | **open** — `/var/run` is a tmpfs at runtime; needs review |
| `package.public_key` | `/etc/ssamd/public.key` | keep as is |

  (The two columns mix the config-reference defaults with the README/guide example
  values; upstream does not ship a single "default" file — `releasedata/` contains only
  the generator scripts.)

- Directory rules from the guide: `package.dir.builtin` **must exist before startup**
  (the daemon does not create it); `downloaded`, `data` and `oci_bundle` are created on
  startup; `oci_bundle` must be an absolute path; none of these may live under a
  writable directory shared by several users.
- `/data` layout: `meta-leda-distro/files/wic/qemux86-64-uboot-efi.wks` has a 3 GB
  ext4 partition labelled `data` built from `sdv-image-data`, whose content mirrors the
  rootfs layout (`/data/var/lib/...`). Note the caveat for testing: `runqemu` on
  `sdv-image-full` boots a single ext4 rootfs, so `/data` is a plain directory there;
  the partitioned layout only exists when booting `sdv-image-all`.
- ext4 project quota is only needed for packages setting `container.storage_limit`;
  optional kernel feature.

## 3. Building an SSAM package (the "helloworld" example)

Upstream Quick start step 3 has two variants; both need tools that are **not** in the
current image:

- `ssam-wrap` needs `lz4`, `erofs-utils`, `fakeroot`, `cryptsetup`; Docker image
  conversion additionally needs `umoci` and `skopeo >= 1.15`.
  Checked in `sdv-image-full`: only `lz4` is present — `erofs-utils`, `cryptsetup`,
  `fakeroot`, `umoci`, `skopeo` are missing.
- Verified working already: `ssam-wrap --prepare <name> <workspace>` writes
  `config.toml`, `runtime.json`, `seccomp.json` and `pkgfs/`.
- Verified failing: `ssam-wrap <workspace>` stops at “Private key file path is
  required” → pass `--private-key`.
- For x86-64 the Docker path needs `--src-oci-arch amd64` (default is `arm64`,
  confirmed in the guest's `--help` output) and network access to Docker Hub.
- The generated `config.toml` defaults `mac = true`, which needs a matching AppArmor
  profile on the target; set `mac = false` for the sample.
- Upstream copies `/etc/resolv.conf` into `pkgfs/etc/`; use the image's file, not the
  build host's.
- **Decision:** produce `.ssam` packages at build time — a recipe (or a small
  `ssam-package.bbclass`) that runs `ssam-wrap` on the build host and installs the
  resulting file into `package.dir.builtin`. The guest and the plain-host-tool routes
  are dropped.
  What that needs, and what is known so far:
  - `DEPENDS += "ssam-wrap-native"`: native variants are created automatically from a
    cross recipe when something depends on them (verified: `native.bbclass` comment
    “Native packages are built indirectly via dependency”). **unverified:** that our
    cargo-based recipe nativizes cleanly — OE does build native rust/cargo tools
    (`cargo_common.bbclass` has `BASEDEPENDS:append = " cargo-native"`, and
    `rust-native`/`cargo-native` recipes exist), but a run is needed.
  - host tools for the filesystem step: `erofs-utils` (`mkfs.erofs`), `lz4`,
    `cryptsetup`, `fakeroot` — all available as recipes in the vendored layers
    (`openembedded-core/meta/recipes-devtools/erofs-utils`,
    `openembedded-core/meta/recipes-support/lz4`,
    `meta-openembedded/meta-oe/recipes-crypto/cryptsetup`, and bitbake already provides
    `fakeroot`); `umoci` and `skopeo >= 1.15` only for Docker/OCI conversion
    (`meta-virtualization/recipes-containers/{umoci,skopeo}`).
  - the private signing key has to exist at build time → keep the example key in this
    recipe directory and pass `--private-key`; the public half is installed into the
    image (section 1).
  - the built `.ssam` file is installed into `package.dir.builtin`, which must exist
    before `ssamd` starts (section 2).
  - Docker Hub access needs network at build time, which the sandboxed build does not
    have → prefer the “from scratch” variant, or fetch OCI content with a separate
    fetcher and feed `oci-archive:` to `--pkgfs-src`.
  - keep `mac = false` in the generated `config.toml` unless an AppArmor profile is
    shipped with the package.

## 4. Configure and start the daemon

Split the upstream steps 4–5 for BitBake/QEMU:

- generate `ssamd.toml` (and optionally `ssamd.service`) at build time with
  `gen_ssamd_conf.sh`, install both next to the binary;
- install `/etc/ssamd/public.key` and create `package.dir.builtin`;
- rebuild the image, boot QEMU, start the service (`systemctl start ssamd` or let the
  unit autostart), then verify.

## 5. Smoke test

- `ssam list` is the upstream verification command (expected output: package listed as
  `Ready`).
- Package containers run as systemd services named after the package; check
  `journalctl --no-pager -u <package>`.
- Guest caveats from the run: no `timeout` command, BusyBox `head` has no `-c`, login
  shell is `zsh` on `sdv-image-full` — see `docs/agents-integrationtesting.md`.

## 6. Update `meta-leda-components/recipes-sdv/mobis-oss/README.md`

Record what the runtime test showed (already added as a “Runtime test” section) and
keep the packagegroup decision open.
