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
public class SecurityException extends ReflectiveOperationException {

    @Serial
    private static final long serialVersionUID = -6436783431048845346L;

    /**
     * No arg construction
     */
    public SecurityException() {
    }

    /**
     * with error message
     * @param message error message
     */
    public SecurityException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param e previous exception
     */
    public SecurityException(String message , Throwable e) {
        super(message , e);
    }

    /**
     * previous exception
     * @param e previous exception
     */
    public SecurityException(Throwable e) {
        super(e);
    }
}
