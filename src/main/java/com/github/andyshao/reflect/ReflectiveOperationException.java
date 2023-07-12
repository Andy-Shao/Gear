package com.github.andyshao.reflect;

import java.io.Serial;

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

    @Serial
    private static final long serialVersionUID = 4166365574371173305L;

    /**
     * No arg construction
     */
    public ReflectiveOperationException() {
    }

    /**
     * with error message
     * @param message error message
     */
    public ReflectiveOperationException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param a previous exception
     */
    public ReflectiveOperationException(String message , Throwable a) {
        super(message , a);
    }

    /**
     * previous exception
     * @param e previous exception
     */
    public ReflectiveOperationException(Throwable e) {
        super(e);
    }
}
