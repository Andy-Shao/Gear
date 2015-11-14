package com.github.andyshao.reflect;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 14, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class ClassNotFoundException extends ReflectiveOperationException {

    private static final long serialVersionUID = 564884292254566531L;

    public ClassNotFoundException() {
    }

    public ClassNotFoundException(String message) {
        super(message);
    }

    public ClassNotFoundException(String message , Throwable exception) {
        super(message , exception);
    }

    public ClassNotFoundException(Throwable exception) {
        super(exception);
    }
}
