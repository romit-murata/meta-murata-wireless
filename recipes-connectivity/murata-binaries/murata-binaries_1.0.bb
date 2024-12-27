SUMMARY = "Murata Binaries"
LICENSE = "BSD-3-Clause"

LIC_FILES_CHKSUM = "file://${S}/nxp-linux-calibration/LICENSE;md5=ffa10f40b98be2c2bc9608f56827ed23"

SRC_URI = " \
	git://github.com/murata-wireless/nxp-linux-calibration;protocol=http;branch=master;destsuffix=nxp-linux-calibration;name=nxp-linux-calibration \
	file://switch_module.sh \
	file://mlanutl.32-bit \
	file://mlanutl.64-bit \
"

SRCREV_nxp-linux-calibration="3f04b4cd4fdfa46d210cd93cb8e343aaf643f05b"

SRCREV_default = "${AUTOREV}"

S = "${WORKDIR}"
B = "${WORKDIR}"
DEPENDS = " libnl "

do_compile () {
	echo "Compiling: "
        echo "Testing Make        Display:: ${MAKE}"
        echo "Testing bindir      Display:: ${bindir}"
        echo "Testing base_libdir Display:: ${base_libdir}"
        echo "Testing sysconfdir  Display:: ${sysconfdir}"
        echo "Testing S  Display:: ${S}"
        echo "Testing B  Display:: ${B}"
        echo "Testing D  Display:: ${D}"
        echo "WORK_DIR :: ${WORKDIR}"
	echo "MACHINE TYPE :: ${MACHINE}"
        echo "PWD :: "
        pwd
}

DO_INSTALL_64BIT_BINARIES = "no"
DO_INSTALL_64BIT_BINARIES_mx6 = "no"
DO_INSTALL_64BIT_BINARIES_mx7 = "no"
DO_INSTALL_64BIT_BINARIES_mx8 = "yes"

do_install () {
	echo "Installing: "
	install -d ${D}/usr/sbin

    # Install /lib/firmware/nxp folder
    install -d ${D}/lib/firmware/nxp
    install -d ${D}/lib/firmware/nxp/murata
    install -d ${D}/lib/firmware/nxp/murata/files
	install -d ${D}/lib/firmware/nxp/murata/files/1XK
    install -d ${D}/lib/firmware/nxp/murata/files/1XL
    install -d ${D}/lib/firmware/nxp/murata/files/1ZM
    install -d ${D}/lib/firmware/nxp/murata/files/1YM
    install -d ${D}/lib/firmware/nxp/murata/files/2DS
    install -d ${D}/lib/firmware/nxp/murata/files/2DL
    install -d ${D}/lib/firmware/nxp/murata/files/2EL

#	Based on MACHINE type
	install -m 755 ${S}/switch_module.sh ${D}/usr/sbin/switch_module.sh

#	Install nxp linux calibration files
	install -m 444 ${S}/nxp-linux-calibration/murata/files/1XK/* ${D}/lib/firmware/nxp/murata/files/1XK
	install -m 444 ${S}/nxp-linux-calibration/murata/files/1XL/* ${D}/lib/firmware/nxp/murata/files/1XL
	install -m 444 ${S}/nxp-linux-calibration/murata/files/1YM/* ${D}/lib/firmware/nxp/murata/files/1YM
	install -m 444 ${S}/nxp-linux-calibration/murata/files/1ZM/* ${D}/lib/firmware/nxp/murata/files/1ZM
	install -m 444 ${S}/nxp-linux-calibration/murata/files/2DS/* ${D}/lib/firmware/nxp/murata/files/2DS
	install -m 444 ${S}/nxp-linux-calibration/murata/files/2DL/* ${D}/lib/firmware/nxp/murata/files/2DL
	install -m 444 ${S}/nxp-linux-calibration/murata/files/2EL/* ${D}/lib/firmware/nxp/murata/files/2EL

	install -m 444 ${S}/nxp-linux-calibration/murata/files/bt_power_config_1.sh ${D}/lib/firmware/nxp/murata/files
    install -m 777 ${S}/nxp-linux-calibration/murata/files/wifi_mod_para_murata.conf ${D}/lib/firmware/nxp/murata/files
    install -m 755 ${S}/nxp-linux-calibration/murata/switch_regions.sh ${D}/usr/sbin/switch_regions.sh
    install -m 444 ${S}/nxp-linux-calibration/murata/README.txt ${D}/lib/firmware/nxp/murata/README.txt

    if [ ${TARGET_ARCH} = "aarch64" ]; then
		install -m 755 ${WORKDIR}/mlanutl.64-bit ${D}/usr/sbin/mlanutl
	else
		install -m 755 ${WORKDIR}/mlanutl.32-bit ${D}/usr/sbin/mlanutl
	fi
}

PACKAGES =+ "${PN}-mfgtest"

FILES:${PN} += "/lib/firmware"
FILES:${PN} += "/lib/firmware/*"
FILES:${PN} += "${bindir}"
FILES:${PN} += "${sbindir}"
FILES:${PN} += "{sysconfdir}/firmware"
FILES:${PN} += "/lib"
FILES:${PN} += "{sysconfdir}/firmware/nxp"
FILES:${PN} += "{sysconfdir}/firmware/nxp/murata"
FILES:${PN} += "{sysconfdir}/firmware/nxp/murata/files"
FILES:${PN} += "{sysconfdir}/firmware/nxp/murata/1XK"
#FILES:${PN} += "/usr/sbin/wpa_supplicant"

INSANE_SKIP:${PN} += "build-deps"
INSANE_SKIP:${PN} += "file-rdeps"

