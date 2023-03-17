OTA Client for cloud connection
Backend: Eclipse Backend Function Bindings endpoints

Installation and configuration

1. Add ghcr.io credentials to /etc/container-management/config.json
   For example, see /etc/ota-client/container-management-config.json.template

   Restart the Kanto Container Management service to pick up the new configuration:

   systemctl restart container-management

2. Prepare Device Certificates
   The certificates are bound to the device name. Please prepare them and be sure that they are working before continue.

   The device certs should be placed under /etc/ota-client/config/certs folder:

    /etc/ota-client/config/certs/device.crt
    /etc/ota-client/config/certs/device.key

    Note: The SSL Trust CA Certificate may need to be updated. By default, it is set to QuoVadis Root CA:
    /etc/ssl/certs/QuoVadis_Root_CA_2_G3.pem

   You may change the certificate by editing the /etc/ota-client/ota-config.json

3. Create and start the OTA Client container:

   cd /data/var/containers/manifests_dev
   mv ota-client.json.disabled ota-client.json
   kanto-auto-deployer

4. Check OTA Client Container

   kantui
   kanto-cm list
   cat /var/lib/container-management/containers/***ID***/json.log 

Offline Installation
1. Download tar package
2. Import container image:
   sudo ctr --namespace kanto-cm i import --base-name ota-client/arm64 ota-client-v0.0.2.tar --digests=true
3. Verify image has been imported
   sudo ctr --namespace kanto-cm i ls
4. Continue with steps from previous chapter
