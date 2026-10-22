# Eclipse Leda Maintainer Guidance

Eclipse Leda is a Poky-based, experimental SDV playground distributed as ready-to-use images for hackathons, learning, and demonstrations.

This repository defines the `meta-leda` and is meant to be reusable by other, external Yocto projects without using the Leda distro itself.
External projects should be able to pick single recipes or packagegroups from `meta-leda` and integrate them into their own targets.

- Main layer config is in `config/layer.conf`
- Machine-specific recipes land in `meta-leda-bsp` (Board Support Pack)
- Recipes in `meta-leda-components` must be independent of the actual distro.
- Leda distro-specific configuration live in `meta-leda-distro`

## Build and configuration

- Use kas as the entry point for preparing and building images. Config files for kas are the main `./.config.yaml` (default config) and the config files in `kas/`.
- Keep pins reproducible. Treat the latest Yocto LTS as the baseline; maintain compatibility with other supported releases and track the current development release where practical.
- This is a BitBake-built project, not a general-purpose apt-based system. Add or change target software through the appropriate Yocto layer, recipe, or image configuration.
- Preserve compatibility for existing users where practical. Clearly flag changes that may affect image behavior, deployment, or clients for review.

## Maintenance

- Prefer actively maintained upstream software and relevant Eclipse SDV, Eclipse IoT, AGL, COVESA, SOAFEE, and ELISA projects when they fit the use case. Prefer LTS bases while tracking the Yocto development release for early compatibility feedback.
- Before carrying or adding a patch to an external recipe, check upstream issue trackers and existing fixes; prefer an existing layer or upstream fix over copying code or maintaining a local patch. Revisit workarounds when updating their upstream component.
- THe meta-leda layer should be treated as a high-quality metalayer with backwards compatibility and long-term maintenance in mind.
- Raise unusually large additions for maintainer review before accepting the cost or removing existing functionality.
- Keep `README.md` as concise entry points for BitBake/Yocto/OpenEmbedded users. Put detailed internal rationale and contributor documentation in `docs/`.

## Validation

- Run the default build config: `kas build`
  Watch/Grep for ERROR and WARNING messages. BitBake will have detailed logs for each failed build step and will print the log filepath.
  Maximum concurrency for builds is 1, the BitBake server will lock and the second BitBake client will wait until the first instance is done.
  It might be a long-running process when the download-cache or sstate-cache is cold (could take hours).
  If caches are warmed up properly and no changes has been made to the sources, the build will still force rebuilding the final rootfs images. That process will still take about 4-5 minutes.
- There are currently no separate test steps within this repository.
