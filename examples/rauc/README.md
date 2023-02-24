# RAUC Example Certificates

The Certificate Authority Keyring and the public certificate and private key in this directory are generated using `meta-rauc/scripts/openssl-ca.sh`

**Please replace these files with your own!**

They are provided here for developer convenience and for the automated tests.

## Configuration

When replacing the example certificates, the BitBake (`local.conf`) or kas configurations need to reference the new locations.

Please see `kas/.config-kirkstone.yaml` for an example:

        RAUC_KEY_FILE="${TOPDIR}/../examples/rauc/development-1.key.pem"
        RAUC_CERT_FILE="${TOPDIR}/../examples/rauc/development-1.cert.pem"
        RAUC_KEYRING_FILE="${TOPDIR}/../examples/rauc/ca.cert.pem"
