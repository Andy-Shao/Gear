package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GeneralSystemPropertyTest {

    @Test
    public void checkValues() {
        assertEquals(GeneralSystemProperty.AWT_TOOLKIT.toString() , System.getProperty("awt.toolkit"));
        assertEquals(GeneralSystemProperty.FILE_ENCODING.toString() , System.getProperty("file.encoding"));
        assertEquals(GeneralSystemProperty.FILE_ENCODING_PKG.toString() , System.getProperty("file.encoding.pkg"));
        assertEquals(GeneralSystemProperty.FILE_SEPARATOR.toString() , System.getProperty("file.separator"));
        assertEquals(GeneralSystemProperty.JAVA_AWT_GRAPHICSENV.toString() , System.getProperty("java.awt.graphicsenv"));
        assertEquals(GeneralSystemProperty.JAVA_AWT_PRINTERJOB.toString() , System.getProperty("java.awt.printerjob"));
        assertEquals(GeneralSystemProperty.JAVA_CLASS_PATH.toString() , System.getProperty("java.class.path"));
        assertEquals(GeneralSystemProperty.JAVA_CLASS_VERSION.toString() , System.getProperty("java.class.version"));
        assertEquals(GeneralSystemProperty.JAVA_ENDORSED_DIRS.toString() , System.getProperty("java.endorsed.dirs"));
        assertEquals(GeneralSystemProperty.JAVA_EXT_DIRS.toString() , System.getProperty("java.ext.dirs"));
        assertEquals(GeneralSystemProperty.JAVA_HOME.toString() , System.getProperty("java.home"));
        assertEquals(GeneralSystemProperty.JAVA_IO_TMPDIR.toString() , System.getProperty("java.io.tmpdir"));
        assertEquals(GeneralSystemProperty.JAVA_LIBRARY_PATH.toString() , System.getProperty("java.library.path"));
        assertEquals(GeneralSystemProperty.JAVA_RUNTIME_NAME.toString() , System.getProperty("java.runtime.name"));
        assertEquals(GeneralSystemProperty.JAVA_RUNTIME_VERSION.toString() , System.getProperty("java.runtime.version"));
        assertEquals(GeneralSystemProperty.JAVA_SPECIFICATION_NAME.toString() , System.getProperty("java.specification.name"));
        assertEquals(GeneralSystemProperty.JAVA_SPECIFICATION_VENDOR.toString() , System.getProperty("java.specification.vendor"));
        assertEquals(GeneralSystemProperty.JAVA_SPECIFICATION_VERSION.toString() , System.getProperty("java.specification.version"));
        assertEquals(GeneralSystemProperty.JAVA_VENDOR.toString() , System.getProperty("java.vendor"));
        assertEquals(GeneralSystemProperty.JAVA_VENDOR_URL.toString() , System.getProperty("java.vendor.url"));
        assertEquals(GeneralSystemProperty.JAVA_VENDOR_URL_BUG.toString() , System.getProperty("java.vendor.url.bug"));
        assertEquals(GeneralSystemProperty.JAVA_VERSION.toString() , System.getProperty("java.version"));
        assertEquals(GeneralSystemProperty.JAVA_VM_INFO.toString() , System.getProperty("java.vm.info"));
        assertEquals(GeneralSystemProperty.JAVA_VM_NAME.toString() , System.getProperty("java.vm.name"));
        assertEquals(GeneralSystemProperty.JAVA_VM_SEPECIFICATION_VENDOR.toString() , System.getProperty("java.vm.specification.vendor"));
        assertEquals(GeneralSystemProperty.JAVA_VM_SPECIFICATION_NAME.toString() , System.getProperty("java.vm.specification.name"));
        assertEquals(GeneralSystemProperty.JAVA_VM_SPECIFICATION_VERSION.toString() , System.getProperty("java.vm.specification.version"));
        assertEquals(GeneralSystemProperty.JAVA_VM_VENDOR.toString() , System.getProperty("java.vm.vendor"));
        assertEquals(GeneralSystemProperty.JAVA_VM_VERSION.toString() , System.getProperty("java.vm.version"));
        assertEquals(GeneralSystemProperty.LINE_SEPARATOR.toString() , System.getProperty("line.separator"));
        assertEquals(GeneralSystemProperty.OS_ARCH.toString() , System.getProperty("os.arch"));
        assertEquals(GeneralSystemProperty.OS_NAME.toString() , System.getProperty("os.name"));
        assertEquals(GeneralSystemProperty.OS_VERSION.toString() , System.getProperty("os.version"));
        assertEquals(GeneralSystemProperty.PATH_SEPARATOR.toString() , System.getProperty("path.separator"));
        assertEquals(GeneralSystemProperty.USER_COUNTRY.toString() , System.getProperty("user.country"));
        assertEquals(GeneralSystemProperty.USER_DIR.toString() , System.getProperty("user.dir"));
        assertEquals(GeneralSystemProperty.USER_HOME.toString() , System.getProperty("user.home"));
        assertEquals(GeneralSystemProperty.USER_LANGUAGE.toString() , System.getProperty("user.language"));
        assertEquals(GeneralSystemProperty.USER_NAME.toString() , System.getProperty("user.name"));
        assertEquals(GeneralSystemProperty.USER_SCRIPT.toString() , System.getProperty("user.script"));
        assertEquals(GeneralSystemProperty.USER_TIMEZONE.toString() , System.getProperty("user.timezone"));
        assertEquals(GeneralSystemProperty.USER_VARIANT.toString() , System.getProperty("user.variant"));
    }
}
