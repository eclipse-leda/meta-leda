# Building recipes for Rust packages

Cargo-BitBake (see https://github.com/meta-rust/cargo-bitbake) is used to generate recipes for Rust applications using Cargo.

## Manual generation

Pre-Requisites:
- Install Rust and Cargo: `curl https://sh.rustup.rs -sSf | sh`
- Source the environment: `source "$HOME/.cargo/env"`
- Install OpenSSL development package: `sudo apt install libssl-dev`
- Install the Cargo BitBake Recipe Generator: `cargo install --locked cargo-bitbake`
- Clone the sources of the target software package
- Switch to the cloned source directory
- If it does not exist, add the `repository` field to the `Cargo.toml` (into the `[package]` section at the top), pointing to the exact git src url:
  ```
  [package]
  name = "..."
  version = "..."
  ...
  repository = "https://github.com/<org>/<repo>"
  ```
- Run the generator: `cargo bitbake`
- Compare or copy the generated .bb file to the target metalayer: `cp <component>_*.bb .../meta-leda-components/recipes-sdv/<target-package>/`
- Adapt the <recipe>.bb file and update the license filename and license file checksum.
- If you need to add custom build functions, create/adapt the file `<recipe>.inc` and include the steps there.

## Troubleshootinjg

Installing `cargo-bitbake` may install an older verfsion, which may have trouble parsing Cargo files, such as `Cargo.lock` versions.
To build a newer version of cargo-bitbake, try installing from source:

```
cargo install cargo-bitbake \
  --git https://github.com/meta-rust/cargo-bitbake \
  --rev 6152cf1811f716798d5f4feb9eeef8dd5c5faf6f \
  --locked --force
```

## Generating it using rust-native from within BitBake environment

See https://docs.yoctoproject.org/ref-manual/classes.html#cargo-update-recipe-crates

Pure BitBake:
```shell
bitbake -c update_crates <recipe>
```

When using kas:
```shell
kas build --cmd update_crates --target <recipe>
```
