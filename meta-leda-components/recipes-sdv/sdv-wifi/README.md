## Wi-Fi connection

Wi-Fi connection can be created as follows:

`wpa_passphrase <SSID> <Passphrase> >> /etc/wpa_supplicant.conf`

`systemctl restart wpa`

Some usefull commands are:

`ip a`

`ip link set wlan0 up`

`iw wlan0 info`

`iw wlan0 scan`

## Pre-deployed Wi-Fi

The pre-deployed Wi-Fi connection cen be set when provided file:

`/boot/wpa_supplicant.conf`