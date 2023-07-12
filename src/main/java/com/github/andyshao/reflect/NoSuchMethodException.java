package com.github.andyshao.reflect;

import java.io.Serial;

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
    @Serial
    private static final long serialVersionUID = -5543614410611301950L;

    /**
     * No arg construction
     */
    public NoSuchMethodException() {
    }

    /**
     * with error message
     * @param message error message
     */
    public NoSuchMethodException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public NoSuchMethodException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * previous exception
     * @param exception previous exception
     */
    public NoSuchMethodException(Throwable exception) {
        super(exception);
    }
}
