# Raspberry Pi 4 WiFi Package modifications

The Raspberry Pi 4 WiFi package uses upstream `linux-firmware-bcm43455` and
does not include the RPi-Distro BCM43456 firmware due to Synaptics licensing.

The Raspberry Pi 4 Model B (BCM43455) is supported.

Boards requiring BCM43456 firmware, such as some Compute Module variants,
are not covered by this WiFi configuration.

Usage recommendation in `local.conf`:

```sh
# The Pi 4 Model B uses BCM43455; avoid RPi-Distro firmware packages,
# including the Synaptics-licensed BCM43456 variant.
MACHINE_EXTRA_RRECOMMENDS:remove = " linux-firmware-rpidistro-bcm43455 linux-firmware-rpidistro-bcm43456"
```
