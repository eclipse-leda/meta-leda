BLUEPRINTS_DIR ??= "/var/containers/blueprints"

DEPENDS += " pkgconfig-native openssl"

cargo_do_compile:prepend() {
    export PKG_CONFIG_ALLOW_CROSS=1
    export BLUEPRINTS_DIR="${BLUEPRINTS_DIR}"
}
