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
public class IllegalAccessException extends ReflectiveOperationException {

    @Serial
    private static final long serialVersionUID = 7602834395539260320L;

    /**
     * No arg construction
     */
    public IllegalAccessException() {
    }

    /**
     * With error message
     * @param message error message
     */
    public IllegalAccessException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param e previous exception
     */
    public IllegalAccessException(String message , Throwable e) {
        super(message , e);
    }

    /**
     * With previous exception
     * @param e previous exception
     */
    public IllegalAccessException(Throwable e) {
        super(e);
    }
}
