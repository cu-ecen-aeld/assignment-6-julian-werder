# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-3-julian-werder.git;protocol=ssh;branch=main"

PV = "1.0+git"
SRCREV = "35a281c91d3d2ca0e13e4d41b8177cc7fc787388"

# The git checkout is unpacked to ${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}; the sources live in its server/ subdirectory
S = "${UNPACKDIR}/${BB_GIT_DEFAULT_DESTSUFFIX}/server"

FILES:${PN} += "${bindir}/aesdsocket ${sysconfdir}/init.d/aesdsocket-start-stop"

TARGET_LDFLAGS += "-pthread -lrt"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesdsocket-start-stop"
# Start last (S99) in the multi-user runlevels; stop first (K01) on halt/reboot so it exits cleanly and removes its data file
INITSCRIPT_PARAMS:${PN} = "defaults 99 01"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	install -d ${D}${bindir}
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket-start-stop ${D}${sysconfdir}/init.d/aesdsocket-start-stop
}
