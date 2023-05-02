#!/bin/sh
# /********************************************************************************
# * Copyright (c) 2023 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Eclipse Public License 2.0 which is available at
# * http://www.eclipse.org/legal/epl-2.0
# *
# * SPDX-License-Identifier: EPL-2.0
# ********************************************************************************/
# Use: 
#   wpa_passphrase <SSID> <Passphrase> >> /etc/wpa_supplicant.conf
# in order to create the file
# Helpful commands:
#   iw wlan0 info
#   iw wlan0 scan
#   iw wlan0 link

if [ -e /boot/wpa_supplicant.conf ]; then
  cp /boot/wpa_supplicant.conf /etc
fi

if [ ! -e /etc/wpa_supplicant.conf ]; then
  exit
fi

/sbin/ip link set wlan0 up
/usr/sbin/wpa_supplicant -B -Dnl80211 -iwlan0 -c/etc/wpa_supplicant.conf
/sbin/udhcpc -i wlan0
