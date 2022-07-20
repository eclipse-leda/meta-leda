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
echo "Will call dapr init -k --wait"

export KUBECONFIG=/etc/rancher/k3s/k3s.yaml

if ! dapr status -k;
then
    kubectl delete namespace dapr-system
    dapr init -k --wait --timeout 600
    if [ "$?" = "0" ];
    then
        if ! dapr status -k;
        then
        # DAPR initialized successfully, remove initialization from systemd
            systemctl disable dapr-init.service
            echo "Dapr installed successfully. dapr-init.service was disabled"
            exit 0
        fi
    fi
else
    systemctl disable dapr-init.service
    echo "dapr status indicates dapr is installed and currently activating"
    exit 0
fi

exit 1