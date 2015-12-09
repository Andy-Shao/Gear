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
public class ReflectiveOperationException extends RuntimeException {

    private static final long serialVersionUID = 4166365574371173305L;

    public ReflectiveOperationException() {
    }

    public ReflectiveOperationException(String message) {
        super(message);
    }

    public ReflectiveOperationException(String message , Throwable a) {
        super(message , a);
    }

    public ReflectiveOperationException(Throwable e) {
        super(e);
    }
}
