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
#  Set the default location of K3S Yaml file as environment variable
#  so that all k3s-related tools (such as k9s) can utilize the correct
#  tokens.
#
export KUBECONFIG=/etc/rancher/k3s/k3s.yaml
