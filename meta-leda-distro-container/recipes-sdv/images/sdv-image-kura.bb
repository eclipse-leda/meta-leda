SUMMARY = "Kura runtime OCI image built from Leda packages"
LICENSE = "Apache-2.0"

inherit image image-oci

IMAGE_FSTYPES = "container oci"
IMAGE_FEATURES = ""
IMAGE_LINGUAS = ""
IMAGE_INSTALL = "base-files base-passwd busybox kura yoriito-vss"
NO_RECOMMENDATIONS = "1"
IMAGE_CONTAINER_NO_DUMMY = "1"

OCI_IMAGE_TAG = "integration"
OCI_IMAGE_ENTRYPOINT = "/usr/bin/kura"
OCI_IMAGE_CMD = "--config-file-path /etc/yoriito/viss/config.yml"
OCI_IMAGE_WORKINGDIR = "/etc/yoriito/viss"
OCI_IMAGE_PORTS = "50052/tcp 50053/tcp"
OCI_IMAGE_APP_RECIPE = "kura"
