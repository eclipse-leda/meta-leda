BLUEPRINTS_DIR ??= "/var/containers/blueprints"

cargo_do_compile:prepend() {
    export BLUEPRINTS_DIR="${BLUEPRINTS_DIR}"
}
