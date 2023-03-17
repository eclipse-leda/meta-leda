OTA Client for cloud connection

Backend: Eclipse Backend Function Bindings endpoints

Installation and configuration
1. Prepare Local MQTT Broker (included in Leda)
2. Install Eclipse Kanto (included in Leda)
3. Add ghcr.io credentials to /etc/container-management/config.json
   For example, see /etc/ota-client/container-management-config.json.template
4. Prepare Config Directory (included in Leda)

        mkdir -p /etc/ota-client/config
        cd /etc/ota-client/config

        drwxr-xr-x    2 root     root          4096 Jan 30 14:52 certs
        -rw-r--r--    1 root     root           445 Jan 27 07:44 ota-config.json
5. Prepare Device Certificates
   The certificates are bound to the device name. Please prepare them and be sure that they are working before continue.
   All certs should be placed under /etc/ota-client/config/certs folder.
6. Prepare ota-config.json (example config is included in Leda)
7. Create OTA Client Container:
   kanto-cm create --name=ota-client --mp="/etc/ota-client/config:/app/config" ghcr.io/softwaredefinedvehicle/sdv-ota-client/ota-client:v0.0.2
8. Run OTA Client Container
   kanto-cm start -n ota-client
9. Check OTA Client Container
   kanto-cm list
10. Logs and Errors
    cat /var/lib/container-management/containers/***ID***/json.log 

Offline Installation
1. Download tar package
2. Import container image:
   sudo ctr --namespace kanto-cm i import --base-name ota-client/arm64 ota-client-v0.0.2.tar --digests=true
3. Verify image has been imported
   sudo ctr --namespace kanto-cm i ls
4. Continue with steps from previous chapter
