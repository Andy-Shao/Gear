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
public class InvocationTargetException extends ReflectiveOperationException {

    @Serial
    private static final long serialVersionUID = 1747951448149260502L;

    /**
     * No arg construction
     */
    public InvocationTargetException() {
    }

    /**
     * With error message
     * @param message error message
     */
    public InvocationTargetException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param e previous exception
     */
    public InvocationTargetException(String message , Throwable e) {
        super(message , e);
    }

    /**
     * With previous exception
     * @param e previous exception
     */
    public InvocationTargetException(Throwable e) {
        super(e);
    }
}
