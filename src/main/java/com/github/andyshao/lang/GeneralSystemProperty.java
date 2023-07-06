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
    /**awt toolkit*/
    AWT_TOOLKIT("awt.toolkit") ,
    /**file encoding*/
    FILE_ENCODING("file.encoding") ,
    /**file encoding pkg*/
    FILE_ENCODING_PKG("file.encoding.pkg") ,
    /**file separator*/
    FILE_SEPARATOR("file.separator") ,
    /**java awt graphicsenv*/
    JAVA_AWT_GRAPHICSENV("java.awt.graphicsenv") ,
    /**java awt printer job*/
    JAVA_AWT_PRINTERJOB("java.awt.printerjob") ,
    /**java class path*/
    JAVA_CLASS_PATH("java.class.path") ,
    /**java class version*/
    JAVA_CLASS_VERSION("java.class.version") ,
    /**java endorsed dirs*/
    JAVA_ENDORSED_DIRS("java.endorsed.dirs") ,
    /**java ext dirs*/
    JAVA_EXT_DIRS("java.ext.dirs") ,
    /**java home*/
    JAVA_HOME("java.home") ,
    /**java io tmpdir*/
    JAVA_IO_TMPDIR("java.io.tmpdir") ,
    /**java library path*/
    JAVA_LIBRARY_PATH("java.library.path") ,
    /**java runtime name*/
    JAVA_RUNTIME_NAME("java.runtime.name") ,
    /**java runtime version*/
    JAVA_RUNTIME_VERSION("java.runtime.version") ,
    /**java specification name*/
    JAVA_SPECIFICATION_NAME("java.specification.name") ,
    /**java specification vendor*/
    JAVA_SPECIFICATION_VENDOR("java.specification.vendor") ,
    /**java specification version*/
    JAVA_SPECIFICATION_VERSION("java.specification.version") ,
    /**java vendor*/
    JAVA_VENDOR("java.vendor") ,
    /**java vendor url*/
    JAVA_VENDOR_URL("java.vendor.url") ,
    /**java vendor url bug*/
    JAVA_VENDOR_URL_BUG("java.vendor.url.bug") ,
    /**java version*/
    JAVA_VERSION("java.version") ,
    /**java vm info*/
    JAVA_VM_INFO("java.vm.info") ,
    /**java vm name*/
    JAVA_VM_NAME("java.vm.name") ,
    /**java vm specification vendor*/
    JAVA_VM_SEPECIFICATION_VENDOR("java.vm.specification.vendor") ,
    /**java vm specification name*/
    JAVA_VM_SPECIFICATION_NAME("java.vm.specification.name") ,
    /**java vm specification version*/
    JAVA_VM_SPECIFICATION_VERSION("java.vm.specification.version") ,
    /**java vm vendor*/
    JAVA_VM_VENDOR("java.vm.vendor") ,
    /**java vm version*/
    JAVA_VM_VERSION("java.vm.version") ,
    /**line separator*/
    LINE_SEPARATOR("line.separator") ,
    /**os arch*/
    OS_ARCH("os.arch") ,
    /**os name*/
    OS_NAME("os.name") ,
    /**os version*/
    OS_VERSION("os.version") ,
    /**path separator*/
    PATH_SEPARATOR("path.separator") ,
    /**user country*/
    USER_COUNTRY("user.country") ,
    /**user dir*/
    USER_DIR("user.dir") ,
    /**user home*/
    USER_HOME("user.home") ,
    /**user language*/
    USER_LANGUAGE("user.language") ,
    /**username*/
    USER_NAME("user.name") ,
    /**user script*/
    USER_SCRIPT("user.script") ,
    /**user timezone*/
    USER_TIMEZONE("user.timezone") ,
    /**user variant*/
    USER_VARIANT("user.variant");

    private final String keyName;

    private GeneralSystemProperty(String keyName) {
        this.keyName = keyName;
    }

    /**
     * get key
     * @return the key
     */
    public String key() {
        return this.keyName;
    }

    @Override
    public String toString() {
        return value();
    }

    /**
     * get value
     * @return the value
     */
    public String value() {
        return System.getProperty(this.keyName);
    }
}
