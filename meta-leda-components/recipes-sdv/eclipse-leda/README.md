# Kanto Auto Deployer via a unix socket

First checkout all submodules:

`git submodule update --init --recursive`

Set the unix the proper unix socket path in:

`src/main.rs`

Build

```bash
cargo build --release
```

Run as root so you can bind to the socket!!



```bash
sudo target/release/kanto-auto-deployer [path to json files]
```

If path is not specified, kanto-auto-deployer uses current.
