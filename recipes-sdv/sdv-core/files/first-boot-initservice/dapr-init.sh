#!/bin/sh
# /********************************************************************************
# * Copyright (c) 2021 Contributors to the Eclipse Foundation
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
#

echo "Will call dapr init -k"
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
dapr init -k --wait --timeout 600

echo "dapr inited"

#job done, remove it from systemd services
systemctl disable dapr-init.service

echo "dapr-init.service was disabled"
