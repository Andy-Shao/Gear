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
public class InvocationTargetException extends ReflectiveOperationException {

    private static final long serialVersionUID = 1747951448149260502L;

    public InvocationTargetException() {
    }

    public InvocationTargetException(String message) {
        super(message);
    }

    public InvocationTargetException(String message , Throwable e) {
        super(message , e);
    }

    public InvocationTargetException(Throwable e) {
        super(e);
    }
}
