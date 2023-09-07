BLUEPRINTS_DIR ??= "/var/containers/bluerpints"

cargo_do_compile:prepend() {
    export BLUEPRINTS_DIR="${BLUEPRINTS_DIR}"
}
