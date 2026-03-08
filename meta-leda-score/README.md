# meta-leda-score

OpenEmbedded / Yocto meta-layer providing recipes for the
[Eclipse S-CORE](https://eclipse.dev/score/) (Safe Open Vehicle Core) automotive
middleware platform.

## Overview

Eclipse S-CORE is an open-source, safety-critical automotive middleware initiative
developed collaboratively by leading industry partners (BMW, Mercedes-Benz, ETAS,
Accenture, Qorix). It provides a modular, service-oriented platform for
high-performance Electronic Control Units (ECUs) targeting Software-Defined
Vehicles (SDVs).

This layer adds Yocto / BitBake recipes for the key S-CORE components, enabling
integration of the Eclipse S-CORE middleware stack into Eclipse Leda-based
distributions.

## Components

| Recipe | Version | Description |
|--------|---------|-------------|
| `eclipse-score-communication` | 0.1.2 | LoLa IPC communication module (ara::com, shared-memory IPC) |
| `eclipse-score-baselibs` | 0.2.4 | Foundational C++ utility libraries (OS abstraction, logging, memory) |
| `eclipse-score-feo` | 1.0.5 | Fixed Order Execution (FEO) runtime framework |
| `packagegroup-eclipse-score` | - | Meta-package installing all S-CORE components |

### Components Under Development

The following S-CORE components are referenced in the issue but are not yet
available as stable, standalone modules:

- **SOME/IP Gateway**: Under architectural planning
  ([eclipse-score/score#914](https://github.com/eclipse-score/score/issues/914)).
  Will be added once the module is available.
- **Time Synchronization Daemon**: Part of the S-CORE platform roadmap.
- **Example Applications**: Available in the
  [eclipse-score/reference_integration](https://github.com/eclipse-score/reference_integration)
  repository. Recipes will be added in a follow-up.

## Bazel Integration

Eclipse S-CORE modules use [Bazel](https://bazel.build/) with
[Bzlmod](https://bazel.build/external/overview#bzlmod) as the build system.
This layer provides:

- **`bazel-native`** recipe: Downloads and installs Bazel 7.x as a native
  (host-side) tool available during cross-compilation builds.
- **`bazel.bbclass`**: A BitBake class that configures the Bazel build
  environment and integrates with the Yocto build lifecycle.

### Bazel Build Limitations

Integrating Bazel into a Yocto build environment has inherent challenges:

1. **Network access**: Bazel's Bzlmod downloads external module dependencies
   at analysis/build time by default. The first build requires network access
   (or a pre-seeded Bazel module cache). Set `BB_NO_NETWORK = "0"` in your
   `local.conf` for these recipes, or pre-populate `BAZEL_REPOSITORY_CACHE`.

2. **Hermetic builds**: For fully offline/hermetic Yocto builds, all Bazel
   module dependencies need to be vendored. Use Bazel's `vendor` command to
   pre-fetch dependencies:
   ```bash
   cd <source-dir>
   bazel vendor --vendor_dir=vendor //...
   ```
   Then set `BAZEL_EXTRA_ARGS += "--vendor_dir=${S}/vendor"` in your recipe.

3. **Cross-compilation**: Bazel manages its own toolchains (GCC, LLVM, Rust)
   independently of the Yocto sysroot. The S-CORE modules use
   `score_bazel_cpp_toolchains` / `toolchains_llvm` for cross-compilation.
   The Yocto `TARGET_ARCH` is mapped to a Bazel platform when
   `BAZEL_CROSS_COMPILE = "1"` is set.

4. **Build artifacts**: Bazel outputs are placed in the `bazel-bin/` symlink
   inside the source directory. The `BAZEL_OUTPUT_FILES` variable lists the
   artifacts to install.

### Bazel Version

S-CORE modules require Bazel 7.x (for Bzlmod / `MODULE.bazel` support).
The `bazel-native` recipe provides Bazel 7.4.1.

## Layer Dependencies

This layer depends on:
- `core` (from poky/meta)

No additional layer dependencies are required.

## Usage

### Adding the Layer

```bash
bitbake-layers add-layer meta-leda/meta-leda-score
```

Or with kas, add the `meta-leda-score` layer to the existing `meta-leda` entry in
your `.config.yaml`:

```yaml
repos:
  meta-leda:
    path: ./
    layers:
      meta-leda-bsp:
      meta-leda-components:
      meta-leda-distro:
      meta-leda-distro-container:
      meta-leda-score:
```

### Installing S-CORE Components

Add to your image recipe or `local.conf`:

```bitbake
IMAGE_INSTALL:append = " packagegroup-eclipse-score"
```

Or add individual components:

```bitbake
IMAGE_INSTALL:append = " eclipse-score-communication eclipse-score-feo"
```

### Allowing Network Access for Bazel

Since Bazel downloads module dependencies during the build, add to your
`local.conf`:

```bitbake
# Allow network access for Bazel dependency fetching
# Remove after pre-seeding the Bazel module cache
BB_NO_NETWORK = "0"
```

## Yocto Compatibility

| Yocto Release | Status |
|---------------|--------|
| Kirkstone (4.0) | Supported |

## References

- [Eclipse S-CORE Project](https://eclipse.dev/score/)
- [S-CORE GitHub Organization](https://github.com/eclipse-score)
- [S-CORE Documentation](https://eclipse-score.github.io/score/main/)
- [Bazel Build System](https://bazel.build/)
- [Eclipse Leda](https://eclipse-leda.github.io/)

## License

Apache License 2.0 - see [LICENSE](../LICENSE) for details.
