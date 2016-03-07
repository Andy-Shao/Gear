package com.github.andyshao.reflect;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 7, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class NoSuchMethodException extends ReflectiveOperationException {
    private static final long serialVersionUID = -5543614410611301950L;

    public NoSuchMethodException() {
    }

    public NoSuchMethodException(String message) {
        super(message);
    }

    public NoSuchMethodException(String message , Throwable exception) {
        super(message , exception);
    }

    public NoSuchMethodException(Throwable exception) {
        super(exception);
    }
}
