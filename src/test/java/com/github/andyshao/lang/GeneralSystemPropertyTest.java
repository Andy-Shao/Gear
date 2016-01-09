package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class GeneralSystemPropertyTest {

    @Test
    public void checkValues() {
        Assert.assertThat(GeneralSystemProperty.AWT_TOOLKIT.toString() ,
            Matchers.is(System.getProperty("awt.toolkit")));
        Assert.assertThat(GeneralSystemProperty.FILE_ENCODING.toString() ,
            Matchers.is(System.getProperty("file.encoding")));
        Assert.assertThat(GeneralSystemProperty.FILE_ENCODING_PKG.toString() ,
            Matchers.is(System.getProperty("file.encoding.pkg")));
        Assert.assertThat(GeneralSystemProperty.FILE_SEPARATOR.toString() ,
            Matchers.is(System.getProperty("file.separator")));
        Assert.assertThat(GeneralSystemProperty.JAVA_AWT_GRAPHICSENV.toString() ,
            Matchers.is(System.getProperty("java.awt.graphicsenv")));
        Assert.assertThat(GeneralSystemProperty.JAVA_AWT_PRINTERJOB.toString() ,
            Matchers.is(System.getProperty("java.awt.printerjob")));
        Assert.assertThat(GeneralSystemProperty.JAVA_CLASS_PATH.toString() ,
            Matchers.is(System.getProperty("java.class.path")));
        Assert.assertThat(GeneralSystemProperty.JAVA_CLASS_VERSION.toString() ,
            Matchers.is(System.getProperty("java.class.version")));
        Assert.assertThat(GeneralSystemProperty.JAVA_ENDORSED_DIRS.toString() ,
            Matchers.is(System.getProperty("java.endorsed.dirs")));
        Assert.assertThat(GeneralSystemProperty.JAVA_EXT_DIRS.toString() ,
            Matchers.is(System.getProperty("java.ext.dirs")));
        Assert.assertThat(GeneralSystemProperty.JAVA_HOME.toString() , Matchers.is(System.getProperty("java.home")));
        Assert.assertThat(GeneralSystemProperty.JAVA_IO_TMPDIR.toString() ,
            Matchers.is(System.getProperty("java.io.tmpdir")));
        Assert.assertThat(GeneralSystemProperty.JAVA_LIBRARY_PATH.toString() ,
            Matchers.is(System.getProperty("java.library.path")));
        Assert.assertThat(GeneralSystemProperty.JAVA_RUNTIME_NAME.toString() ,
            Matchers.is(System.getProperty("java.runtime.name")));
        Assert.assertThat(GeneralSystemProperty.JAVA_RUNTIME_VERSION.toString() ,
            Matchers.is(System.getProperty("java.runtime.version")));
        Assert.assertThat(GeneralSystemProperty.JAVA_SPECIFICATION_NAME.toString() ,
            Matchers.is(System.getProperty("java.specification.name")));
        Assert.assertThat(GeneralSystemProperty.JAVA_SPECIFICATION_VENDOR.toString() ,
            Matchers.is(System.getProperty("java.specification.vendor")));
        Assert.assertThat(GeneralSystemProperty.JAVA_SPECIFICATION_VERSION.toString() ,
            Matchers.is(System.getProperty("java.specification.version")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VENDOR.toString() ,
            Matchers.is(System.getProperty("java.vendor")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VENDOR_URL.toString() ,
            Matchers.is(System.getProperty("java.vendor.url")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VENDOR_URL_BUG.toString() ,
            Matchers.is(System.getProperty("java.vendor.url.bug")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VERSION.toString() ,
            Matchers.is(System.getProperty("java.version")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_INFO.toString() ,
            Matchers.is(System.getProperty("java.vm.info")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_NAME.toString() ,
            Matchers.is(System.getProperty("java.vm.name")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_SEPECIFICATION_VENDOR.toString() ,
            Matchers.is(System.getProperty("java.vm.specification.vendor")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_SPECIFICATION_NAME.toString() ,
            Matchers.is(System.getProperty("java.vm.specification.name")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_SPECIFICATION_VERSION.toString() ,
            Matchers.is(System.getProperty("java.vm.specification.version")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_VENDOR.toString() ,
            Matchers.is(System.getProperty("java.vm.vendor")));
        Assert.assertThat(GeneralSystemProperty.JAVA_VM_VERSION.toString() ,
            Matchers.is(System.getProperty("java.vm.version")));
        Assert.assertThat(GeneralSystemProperty.LINE_SEPARATOR.toString() ,
            Matchers.is(System.getProperty("line.separator")));
        Assert.assertThat(GeneralSystemProperty.OS_ARCH.toString() , Matchers.is(System.getProperty("os.arch")));
        Assert.assertThat(GeneralSystemProperty.OS_NAME.toString() , Matchers.is(System.getProperty("os.name")));
        Assert.assertThat(GeneralSystemProperty.OS_VERSION.toString() , Matchers.is(System.getProperty("os.version")));
        Assert.assertThat(GeneralSystemProperty.PATH_SEPARATOR.toString() ,
            Matchers.is(System.getProperty("path.separator")));
        Assert.assertThat(GeneralSystemProperty.USER_COUNTRY.toString() ,
            Matchers.is(System.getProperty("user.country")));
        Assert.assertThat(GeneralSystemProperty.USER_DIR.toString() , Matchers.is(System.getProperty("user.dir")));
        Assert.assertThat(GeneralSystemProperty.USER_HOME.toString() , Matchers.is(System.getProperty("user.home")));
        Assert.assertThat(GeneralSystemProperty.USER_LANGUAGE.toString() ,
            Matchers.is(System.getProperty("user.language")));
        Assert.assertThat(GeneralSystemProperty.USER_NAME.toString() , Matchers.is(System.getProperty("user.name")));
        Assert.assertThat(GeneralSystemProperty.USER_SCRIPT.toString() ,
            Matchers.is(System.getProperty("user.script")));
        Assert.assertThat(GeneralSystemProperty.USER_TIMEZONE.toString() ,
            Matchers.is(System.getProperty("user.timezone")));
        Assert.assertThat(GeneralSystemProperty.USER_VARIANT.toString() ,
            Matchers.is(System.getProperty("user.variant")));
    }
}
