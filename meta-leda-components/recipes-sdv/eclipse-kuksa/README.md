# Building Recipe for Eclipse Kuksa.VAL

The [Eclipse Kuksa.VAL Databroker](https://github.com/eclipse/kuksa.val/tree/master/kuksa_databroker) is implemented in Rust.
Cargo-BitBake (see https://github.com/meta-rust/cargo-bitbake) is used to generate recipes for Rust applications using Cargo.

Pre-Requisites:

- Install Rust and Cargo: `curl https://sh.rustup.rs -sSf | sh`
- Source the environment: `source "$HOME/.cargo/env"`
- Install OpenSSL development package: `sudo apt install libssl-dev`
- Install the Cargo BitBake Recipe Generator: `cargo install --locked cargo-bitbake`
- Checkout the sources manually once: `git clone https://github.com/eclipse/kuksa.val`
- Switch to the source directory: `cd kuksa.val/kuksa_databroker/databroker-cli`
- Add the `repository` field to the `Cargo.toml` (into the `[package]` section at the top), pointing to the exact git src url:

  ```toml
  [package]
  name = "databroker"
  version = "0.7.1"
  ...
  repository = "https://github.com/eclipse-kuksa/kuksa-databroker"
  ```

- Run the generator: `cargo bitbake`
- Compare or copy the generated .bb file to the target metalayer: `cp databroker-cli_*.bb .../meta-leda-components/recipes-sdv/eclipse-kuksa`
- Adapt the `databroker-cli_<version>.bb` file and update the license filename and license file checksum
- If you need to add custom build functions, create the file `databroker-cli.inc` and include the steps there

## Troubleshootinjg

Installing `cargo-bitbake` may install an older verfsion, which may have trouble parsing Cargo files, such as `Cargo.lock` versions.
To build a newer version of cargo-bitbake, try installing from source:

```sh
cargo install cargo-bitbake \
  --git https://github.com/meta-rust/cargo-bitbake \
  --rev 6152cf1811f716798d5f4feb9eeef8dd5c5faf6f \
  --locked --force
```
