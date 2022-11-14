# ESR Labs - Northstar container runtime

These BitBake recipes will build the Northstar container runtime and the command line tools from https://github.com/esrlabs/northstar

- Clone Northstar repository
- Install build essentials, clang etc. according to readme
  - Install build essentials: `sudo apt-get install build-essential libclang1 squashfs-tools`
  - Install OpenSSL: `apt install pkg-config libssl-dev`
- Build examples: `./examples/build_examples.sh`
- Build Northstar with cargo: `cargo build`
- Build Northstar with cargo: `cargo run --bin northstar`

# Creating BitBake Recipe
- Install cargo-bitbake from https://github.com/meta-rust/cargo-bitbake 
        cargo install --locked cargo-bitbake
- Go to submodule, eg `cd northstar` or `cd northstar-nstar`
- Run `cargo bitbake` to create the initial recipe source with all the dependencies
- Copy generated .bb file to meta-sdv/meta-sdv-components/recipes-sdv/northstar

# Build single recipe
- Source openembedded environment, e.g. `. oe-init-build-env ../build-sdv-x86_64`
- Build recipe: `bitbake northstar`

# Integrate into distribution
- meta-rust is NOT needed since Yocto Honister, as meta-rust has been included into Poky
- Set RUSTVERSION="1.58" so that Rust Edition 2021 is used

# TODO
- The northstar recipes are supposed to be contributed to meta-virtualization
