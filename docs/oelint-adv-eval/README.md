# Evaluating oelint-adv as a BitBake linter

This document records the evaluation of [oelint-adv](https://github.com/priv-kweihmann/oelint-adv)
as a syntax and style checker for the BitBake files in this repository. It is a
research note, not an integration: the tool is currently not part of the
pre-commit hooks and not part of the CI jobs.

The files under evaluation are the BitBake sources of this repository:

- `conf/layer.conf` and the `meta-leda-*/conf/layer.conf` files
- `meta-leda-*/recipes-*/*/*.bb`, `*.bbappend`, `*.inc`, `*.bbclass`

## Tool facts

```sh
pip3 install --user --break-system-packages oelint_adv
oelint-adv --version
```

At the time of this evaluation: `oelint_adv 9.11.2`, `oelint-parser 8.13.2`,
`oelint-data 1.5.25`. The tool knows a Yocto release aware ruleset and
constants database, `--release wrynose` is available.

| Option              | Effect                                                                   |
| ------------------- | ------------------------------------------------------------------------ |
| `--release <rel>`   | Ruleset and constants for a Yocto release (`wrynose` is supported)         |
| `--rulefile <file>` | JSON map `{"rule": "severity"}`, **replaces** the default ruleset          |
| `--print-rulefile`  | Dump the default ruleset as a rulefile, to be edited and reused            |
| `--constantmods +f` | Add the variables/functions of file `f` to the constants database          |
| `--extra-layer <l>` | Load the constants of a known layer (only 26 layers exist for `wrynose`)  |
| `--suppress <rule>` | Turn a rule off                                                            |
| `--hide info`       | Drop informational findings                                                |
| `--exit-zero`       | Always exit 0, findings are reported but never block                       |
| `--fix`             | Rewrite the files automatically (backup files unless `--nobackup`)         |

Findings are written to **stderr** by default (`--output` overrides this), and
the exit code is `1` when anything was found. Parsing 175 BitBake files of this
repository takes about 4 seconds, so the tool is fast enough for a git hook.

## Interaction with kas

oelint-adv does **not** read `build/`, `layer.conf`, or anything else that kas
generates. It parses each file on its own with its own BitBake parser and a
built-in constants database. Therefore:

- No `kas build` is needed before running it, and no guard for an existing
  `build/` directory is needed.
- What it needs instead is being told about things it cannot know: the
  variables of this layer (`--constantmods`) and the variables of upstream
  layers it has data for (`--extra-layer`).

## Baseline: the default ruleset

Running the default ruleset over the BitBake files of this repository gives:

```text
1518 findings over 175 files
 351 error, 560 warning, 607 info
```

That is about 9 findings per file. As a calibration, the same command over 30
random `openembedded-core` recipes gives 840 findings, about 28 per file. The
default ruleset is opinionated, and this layer is already about three times
cleaner than oe-core by that standard. The default ruleset is therefore not
usable as a blocking check.

The rules that dominate the output are false positives for this layer:

| Rule                                | Count | Why it fires here                                                     |
| ----------------------------------- | ----- | --------------------------------------------------------------------- |
| `oelint.vars.noncoreoverride`       | 112   | Flags the standard `FILESEXTRAPATHS:prepend := "${THISDIR}/files:"` idiom in appends |
| `oelint.file.requireinclude`        | 61    | Wants `require` instead of `include`; our machine specific includes are deliberate |
| `oelint.vars.mispell.unknown`       | 63    | Our own variables, see `--constantmods`                               |
| `oelint.file.includenotfound`       | 29    | Cannot expand `${PV}` in `include` paths                              |
| `oelint.append.protvars.*`          | 7     | Treats our `.inc` fragments as bbappends                              |
| `oelint.var.suggestedvar.*`         | 330   | `SECTION` 114, `BUGTRACKER` 111, `CVE_PRODUCT` 105, `SECTION` is deprecated in modern Yocto |
| `oelint.var.mandatoryvar.HOMEPAGE`  | 36    | Packagegroups and configuration snippets have no upstream homepage    |
| `oelint.vars.specific`, `oelint.var.override` | 9, 10 | Does not know our machines, so `:raspberrypi4-64` and repeated `.inc` summaries are reported |
| whitespace and ordering rules | ≈410 | `var.order.*` 150, `notneededspace` 125, `inconspaces` 85, `newline.consecutive` 49 |

## The curated rule set

`oelint-rulefile.json` and `oelint-constants.json` in this directory hold the
curated set. The rulefile keeps ten rules that describe possible defects rather
than style, the constants file teaches the tool the variables of this layer and
the ones its own database is missing (`QB_ROOTFS_OPT`, `dirs755`, …).

```sh
files=$(git ls-files 'meta-leda-*' | grep -E '\.(bb|bbappend|inc|bbclass)$')
oelint-adv --release wrynose --relpaths --quiet \
  --rulefile docs/oelint-adv-eval/oelint-rulefile.json \
  --constantmods +docs/oelint-adv-eval/oelint-constants.json \
  -- $files
```

This gives 34 findings, recorded in `curated-findings.txt` (2026-10-23,
oelint-adv 9.11.2). Ten of them are
parser artifacts: `oelint.vars.mispell` reports `FILES_${PN}` and
`RDEPENDS_${PN}` as `FILES_` and `RDEPENDS_`. The remaining 24 are worth
looking at:

| File and line                                        | Rule                     | Assessment |
| ---------------------------------------------- | ------------------------ | -------------------------------------------------------------- |
| `packagegroup-sdv-rpi4wifi.bb:19`, `sdv-databroker-cli_0.1.bb:55`, `nerdctl_git.bbappend:21` | `oelint.newline.eof` | Real, the files have no final newline |
| `eclipse-score-*.bb`, `blueprint-selector.inc:20` (4) | `oelint.vars.duplicate` | `bazel-native` is added by `bazel.bbclass` and again by the recipes, `openssl` by the `.inc` and the recipe |
| `base-files_%.bbappend:31`, `os-release.bbappend:38` | `oelint.vars.dependsappend` | `DEPENDS = "git-native"` overwrites instead of appending, needs a decision |
| `vss-release{2,3,4,6}.bb`, `container-management_%.bbappend`, `update-manager_%.bbappend`, `sdv-container-*.bb` (8) | `oelint.vars.autorev` | `SRCREV = "${AUTOREV}"` makes the build not reproducible, against the pin policy of this repository |
| `vss-tools_4.0.1.bb:21`                            | `oelint.vars.srcurichecksum` | `md5sum` in `SRC_URI` is deprecated |
| `uds-server_git.bb:23`                             | `oelint.vars.srcurimutualex` | `SRCREV` and a tag checksum for the same item |
| `sdv-container-cache.bbclass:187,188`              | `oelint.task.nomkdir`, `oelint.task.nocopy` | `mkdir`/`cp` in `do_install`, `install` is preferred |
| `wasmedge`, `kibi`, `eclipse-cyclonedds-examples` (3) | `oelint.vars.licfileprefix` | `${S}/` prefix on `LIC_FILES_CHKSUM` is not needed |

## Suggested path forward

The plan is to start with the findings that describe real problems and to
widen the ruleset only once the layer is clean for the rules that are already
enabled. Each step keeps the same command, only the rulefile changes.

1. Fix the current findings of the curated set: the three missing final newlines,
   the duplicated `DEPENDS` entries, the two `DEPENDS =` assignments, the
   deprecated `md5sum` checksum. `${AUTOREV}` and the `${PN}` suffix style are
   deliberate here and should be suppressed instead of fixed.
2. Add the rules that describe structure and packaging correctness once the
   above is clean: `oelint.file.underscores` (recipe file names),
   `oelint.vars.pkgspecific.FILES`, `oelint.var.override`,
   `oelint.vars.filessetting.*`, `oelint.task.network`.
3. Add the upstream metadata rules last: `oelint.file.upstreamstatus`,
   `oelint.file.inappropriatemsg`, `oelint.file.patchsignedoff`,
   `oelint.var.licenseremotefile`. These apply to the patches and the
   container recipes we carry.
4. Keep the pure style rules (`var.order.*`, whitespace, `suggestedvar.*`)
   disabled, they are the ones that even oe-core does not satisfy.

Until then the tool stays a manual step. It is not added to
`.pre-commit-config.yaml`, because even the curated set reports findings that
are deliberate here, and a blocking hook would hide the signal.
