SUMMARY = "SDV Configuration of k3s"
DESCRIPTION = "Custom configuration for Kubernetes"

SRC_URI += "file://README.txt \
            file://10_k3s_kubeconfig.sh \
            file://LICENSE"

# According to https://wiki.yoctoproject.org/wiki/License_Infrastructure_Interest_Group
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=d9fc0efef5228704e7f5b37f27192723"

do_install() {
	install -d ${D}${sysconfdir}/profile.d
	install -m 0755 ${WORKDIR}/10_k3s_kubeconfig.sh ${D}${sysconfdir}/profile.d/
}
