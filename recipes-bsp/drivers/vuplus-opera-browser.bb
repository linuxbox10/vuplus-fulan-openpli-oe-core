DESCRIPTION = "Opera HbbTV browser"
PRIORITY = "optional"
LICENSE = "CLOSED"
SECTION = "base"

DEPENDS = "mpfr gmp"
RREPLACES_${PN} = "vuplus-opera-browser-util"
RCONFLICTS_${PN} = "vuplus-opera-browser-util"
PACKAGES = "${PN}"

SRC_DATE = "20150410_1"
PR = "r2_${SRC_DATE}"

SRC_URI = ""
SRC_FILE = "opera-hbbtv_${SRC_DATE}.tar.gz"
SRC_URI[md5sum] = "d29ca7a0b7f6c9071300cd8dc66f1278"
SRC_URI[sha256sum] = "4c08c9e3605eb6f4b7d3c3e49f59291883e89701794e5dec92e4085106182742"

S = "${WORKDIR}/opera-hbbtv"

do_fetch() {
       if [ ! -e ${DL_DIR}/${SRC_FILE} -a -e /etc/vuplus_browser.pwd ]; then
sshpass -f /etc/vuplus_browser.pwd sftp -o StrictHostKeyChecking=no guestuser@code.vuplus.com << +
get ${SRC_FILE}
bye
+
       fi
       cp -av ${DL_DIR}/${SRC_FILE} ${WORKDIR}/
}

do_unpack() {
       tar xvfz ${SRC_FILE}
}

do_compile() {
}

do_install() {
	install -d ${D}/usr/local/hbb-browser
	cp -avR ${S}/opera/* ${D}/usr/local/hbb-browser/

	install -d ${D}/usr/lib
	cp -avR ${S}/dfb/usr/lib/* ${D}/usr/lib/
}

do_install_append() {
	GST_REQUIRED_VERSION=$(pkg-config --list-all | grep gstreamer-[0-9].* | awk -F "-| " '{print $2}')
	GST_VERSION=$(pkg-config --modversion "gstreamer-$GST_REQUIRED_VERSION >= $GST_REQUIRED_VERSION")
	mv ${D}/usr/local/hbb-browser/root/jsplugins/ooif-gst-$GST_VERSION.so ${D}/usr/local/hbb-browser/root/jsplugins/ooif.so
	rm -f ${D}/usr/local/hbb-browser/root/jsplugins/ooif-gst*.so
	mv ${D}/usr/local/hbb-browser/root/video/videobackend-gst-$GST_VERSION.so ${D}/usr/local/hbb-browser/root/video/videobackend.so
	rm -f ${D}/usr/local/hbb-browser/root/video/videobackend-gst*.so
}

do_package_qa() {
}

sysroot_stage_all() {
}

INHIBIT_PACKAGE_STRIP = "1"
PRIVATE_LIBS_${PN} = "libopera_hbbtv.so \
libdsmcc.so \
libdirect-1.4.so.6 \
libdirectfb-1.4.so.6 \
libfusion-1.4.so.6 \
libdirectfbwm_default.so \
libdirectfb_linux_input.so \
libdirectfb_devmem.so \
libdirectfb_dummy.so \
libdirectfb_fbdev.so \
libidirectfbfont_dgiff.so \
libidirectfbvideoprovider_v4l.so \
libidirectfbvideoprovider_gif.so \
libidirectfbimageprovider_dfiff.so \
libidirectfbimageprovider_gif.so \
libidirectfbimageprovider_jpeg.so \
libicoreresourcemanager_test.so \
libdirectfb_vuplus.so"

FILES_${PN} = "/usr/lib /usr/local"
