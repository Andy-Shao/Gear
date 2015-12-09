package com.github.andyshao.lang;

/**
 * 
 * Descript:Some of general system porperties. A kind of convenient way which
 * can take the system properties.<br>
 * Copyright: Copyright(c) Oct 31, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public enum GeneralSystemProperty {
    AWT_TOOLKIT("awt.toolkit") , FILE_ENCODING("file.encoding") , FILE_ENCODING_PKG("file.encoding.pkg") ,
    FILE_SEPARATOR("file.separator") , JAVA_AWT_GRAPHICSENV("java.awt.graphicsenv") ,
    JAVA_AWT_PRINTERJOB("java.awt.printerjob") , JAVA_CLASS_PATH("java.class.path") ,
    JAVA_CLASS_VERSION("java.class.version") , JAVA_ENDORSED_DIRS("java.endorsed.dirs") ,
    JAVA_EXT_DIRS("java.ext.dirs") , JAVA_HOME("java.home") , JAVA_IO_TMPDIR("java.io.tmpdir") ,
    JAVA_LIBRARY_PATH("java.library.path") , JAVA_RUNTIME_NAME("java.runtime.name") ,
    JAVA_RUNTIME_VERSION("java.runtime.version") , JAVA_SPECIFICATION_NAME("java.specification.name") ,
    JAVA_SPECIFICATION_VENDOR("java.specification.vendor") , JAVA_SPECIFICATION_VERSION("java.specification.version") ,
    JAVA_VENDOR("java.vendor") , JAVA_VENDOR_URL("java.vendor.url") , JAVA_VENDOR_URL_BUG("java.vendor.url.bug") ,
    JAVA_VERSION("java.version") , JAVA_VM_INFO("java.vm.info") , JAVA_VM_NAME("java.vm.name") ,
    JAVA_VM_SEPECIFICATION_VENDOR("java.vm.specification.vendor") ,
    JAVA_VM_SPECIFICATION_NAME("java.vm.specification.name") ,
    JAVA_VM_SPECIFICATION_VERSION("java.vm.specification.version") , JAVA_VM_VENDOR("java.vm.vendor") ,
    JAVA_VM_VERSION("java.vm.version") , LINE_SEPARATOR("line.separator") , OS_ARCH("os.arch") , OS_NAME("os.name") ,
    OS_VERSION("os.version") , PATH_SEPARATOR("path.separator") , USER_COUNTRY("user.country") , USER_DIR("user.dir") ,
    USER_HOME("user.home") , USER_LANGUAGE("user.language") , USER_NAME("user.name") , USER_SCRIPT("user.script") ,
    USER_TIMEZONE("user.timezone") , USER_VARIANT("user.variant");

    private final String keyName;

    private GeneralSystemProperty() {
        this(null);
    }

    private GeneralSystemProperty(String keyName) {
        this.keyName = keyName;
    }

    public String key() {
        return this.keyName;
    }

    @Override
    public String toString() {
        return System.getProperty(this.keyName == null ? super.toString() : this.keyName);
    }

    public String value() {
        return this.toString();
    }
}
