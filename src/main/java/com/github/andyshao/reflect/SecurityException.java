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
public class SecurityException extends ReflectiveOperationException {

    private static final long serialVersionUID = -6436783431048845346L;

    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message , Throwable e) {
        super(message , e);
    }

    public SecurityException(Throwable e) {
        super(e);
    }
}
