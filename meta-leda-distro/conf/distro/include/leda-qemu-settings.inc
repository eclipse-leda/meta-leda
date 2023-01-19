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

# Increase memory for Kanto Container Management to at least 2GB or more
QB_MEM = "-m 4096"
QB_MEM:qemuarm = "-m 2048"

# No graphical user interface
# QB_OPT_APPEND:append = " -serial mon:stdio -serial null"
QB_OPT_APPEND:append = " -nographic"

# Emulate a CAN-Bus device
QB_OPT_APPEND:append = " -object can-bus,id=canbus0 "
QB_OPT_APPEND:append = " -device kvaser_pci,canbus=canbus0"

QB_DEFAULT_FSTYPE = "ext4"
