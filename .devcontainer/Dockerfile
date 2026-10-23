# /********************************************************************************
# * Copyright (c) 2022 Contributors to the Eclipse Foundation
# *
# * See the NOTICE file(s) distributed with this work for additional
# * information regarding copyright ownership.
# *
# * This program and the accompanying materials are made available under the
# * terms of the Apache License 2.0 which is available at
# * https://www.apache.org/licenses/LICENSE-2.0
# *
# * SPDX-License-Identifier: Apache-2.0
# ********************************************************************************/

# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.245.2/containers/debian/.devcontainer/base.Dockerfile
ARG VARIANT="bookworm"
FROM mcr.microsoft.com/vscode/devcontainers/base:2-${VARIANT}

# Install Dependencies
RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install --no-install-recommends socat file gawk wget acl git diffstat unzip texinfo \
        gcc build-essential chrpath socat cpio python3 python3-pip python3-pexpect xz-utils debianutils \
        iputils-ping python3-git python3-jinja2  libsdl1.2-dev pylint xterm python3-subunit \ 
        mesa-common-dev zstd lz4 tmux mc skopeo fdisk ruby-full jq shellcheck \
        libvirt-clients libvirt-daemon-system qemu-system-x86 qemu-system-arm qemu-kvm \
        squashfs-tools rauc python3-newt

RUN DEBIAN_FRONTEND=noninteractive apt-get -y install ca-certificates \
    curl \
    gnupg \
    lsb-release

# Install kas tool to set up Yocto build environment
RUN apt-get install -y pipx
RUN pipx install kas
USER vscode
RUN pipx install kas
