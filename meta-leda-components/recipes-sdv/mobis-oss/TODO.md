- Currently, `ssamd` looks for its config file in the same location as the binary, that is pretty unusual for a real installation.
  Imho, we should change the default to `/etc/ssam/ssamd.toml` in the compiled version, so that the default installation rules can be applied.

Read `https://raw.githubusercontent.com/mobis-oss/ssam/refs/heads/main/README.md`
Read `https://github.com/mobis-oss/ssam/blob/main/docs/guides/serving-ssam.md`

Proceed with step 2 from quick start chapter, but acknowledge some modifications in the steps which are necessary to make it work in our BitBake / QEMU build environment:

1) Generate an example signing key pair

- Put the key files into `examples/ssam/`.
- Add a very short README.md to explain what they are used for.

2) Implement the systemd integration of ssamd

- Construct and install a default `ssamd.toml` in the image.
- Put the default config into `meta-leda-components/recipes-sdv/mobis-oss/files/`
- The default config is supposed to be overridden/customized by integrators (e.g. users of meta-leda) for their distro using bbappend mechanisms.
- We need to adapt the default locations to make it work for Leda, as we have a different disk partition setup.
  Data (container images are considered to be data and live on a larger partition - in theory, the rootfs is readonly on ECUs).

| Option | SSAM Default Path | New customized path for meta-leda |
| --- | --- | --- |
| `--builtin-pkgs-dir` | `/var/lib/ssamd/builtin` | `/data/var/lib/ssamd/builtin` |
| `--downloaded-pkgs-dir` | `/var/lib/ssamd/downloaded` | `/data/var/lib/ssamd/downloaded` |
| `--pkgs-data-root` | `/var/lib/ssamd/data` | `/data/var/lib/ssamd/data` |
| `--oci-bundle-dir` | `/var/run/ssamd/oci-bundle` | `/var/run/ssamd/oci-bundle` ?? Unclear, needs to be reviewed |
| `--public-key-file` | `/etc/ssamd/public.key` | `/etc/ssamd/public.key` can be kept as is | 

- We may put the locations into the `ssamd.toml`, see https://github.com/mobis-oss/ssam/blob/main/docs/reference/daemon-config.md for the correct config keys.

3) Create a ssam package from the Docker helloworld OCI container

- Find out how to run "ssam-wrap" executable on build host
  Alternatively, discuss whether we should build a BBCLASS for ssam-packages so that bitbake can actually run these steps to build an SSAM-formatted container.
- Do NOT copy the build hosts `/etc/resolv.conf`, we need to use the one from the sdv-image-full image.

3) Configure and start the daemon

These steps are explained in the readme as to be run on the host, but for BitBake / QEMU we need to split it up:

- Generate the config on the host ir within a bb recipe
- Install the ssam config into the image
- Rebuild the image (kas build ...)
- Start runqemu
- Start the ssamd within qemu guest or have it installed as systemd service (preferred)

4) Update `meta-leda-components/recipes-sdv/mobis-oss/README.md`

5) Smoketest

- Start runqemu
- Wait until systemd services are up and running
- Verify `helloworld` got installed and is running by using `ssam list` command.

## Building SSAM packages

Read https://github.com/mobis-oss/ssam/blob/main/docs/reference/package-config.md
Read https://github.com/mobis-oss/ssam/blob/main/docs/reference/ssam-wrap.md

ssam-wrap and its dependencies are native tools for the build host, they are not supposed to be installed on the target machine.

Generate a BitBake Class `ssam-container.bbclass` in `meta-leda-components/classes`
Generate `ssam-container-helloworld.bb` into `meta-leda-components/recipes-sdv/mobis-oss-containers/` inheriting the `ssam-container` bbclass.
The bbclass/recipe is supposed to package an OCI container from docker and convert it into a SSAM container package.



