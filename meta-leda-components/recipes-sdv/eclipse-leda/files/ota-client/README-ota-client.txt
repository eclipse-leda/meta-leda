# OTA Client for cloud connection

Backend: Eclipse Backend Function Bindings endpoints

## Installation and configuration

1. Add ghcr.io credentials to /etc/container-management/config.json
   For example, see /etc/ota-client/container-management-config.json.template

   sdv-kanto-ctl add-registry -h ghcr.io -u github -p <YOUR_GH_PAT>

2. Restart the Kanto Container Management service to pick up the new configuration:

   systemctl restart container-management

3. Prepare Device Certificates
   The certificates are bound to the device name. Please prepare them and be sure that they are working before continue.

   The device certs should be placed under /data/var/certificates folder:

    /data/var/certificates/device.crt
    /data/var/certificates/device.key
    /data/var/certificates/ca.pem

   You may change the certificate filenames by editing the /etc/ota-client/ota-config.json.
   Note: If you need to change the location, you also need to modify the mounts in /data/var/containers/manifests/ota-client.json accordingly.

4. Configure Device ID, edit /etc/ota-client/ota-config.json:

   "mqtt-client-id":"<<PUT DEVICE ID HERE>>"

5. Create and start the OTA Client container:

   cd /data/var/containers/manifests
   mv ota-client.json.disabled ota-client.json
   kanto-auto-deployer

6. Check OTA Client Container

   kantui
   kanto-cm list
   cat /var/lib/container-management/containers/***ID***/json.log 


# Offline Installation

1. Download tar package
2. Import container image:
   sudo ctr --namespace kanto-cm i import --base-name ota-client/arm64 ota-client-v0.0.2.tar --digests=true
3. Verify image has been imported
   sudo ctr --namespace kanto-cm i ls
4. Continue with steps from previous chapter
