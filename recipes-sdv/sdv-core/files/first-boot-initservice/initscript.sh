#!/bin/sh

logger "starting initscript"

# do some work here. Mount rootfs as rw if needed.
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
dapr init -k

logger "initscript work done"

#job done, remove it from systemd services
systemctl disable initscript.service

logger "initscript disabled"
