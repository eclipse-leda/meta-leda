# Eclipse Leda - Metalayer - Package Groups

## Core

Minimum set of required system components to run an SDV.EDGE stack:
- Container Runtime
- RAUC Update Service for OTA Self Update
- Mosquitto
- Default Containers, e.g. Data Broker

## Additions

Additional system components which makes it easier to implement additional use cases or experiments:
- Kanto: Suite Connector, File-Upload, System-Metrics etc.
- Northstar: northstar daemon
- SSH server
- RAUC Hawkbit Connector
- OpenVPN

## Examples

Example applications and dependencies (such as CAN drivers) which are predeployed for quickstart tutorials.
- Seat Service incl. CAN Utils

## Tools

Convenient tools for developing or troubleshooting:
- Basic system tools: htop, jq, nano, sudo
- Mosquitto Utilities: mosquitto-clients
- Leda Utilities: sdv-core-utils
- Container tools: skopeo, kantui, northstar nstar

## Rescue

System components needed to perform a "rescue" case, such as resetting the device to factory settings or reestablishing the cloud registration
- SSH Server
- RAUC