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
public class InstantiationException extends ReflectiveOperationException {

    @Serial
    private static final long serialVersionUID = -1040253531882507911L;

    /**
     * No arg construction
     */
    public InstantiationException() {
    }

    /**
     * With error message
     * @param message error message
     */
    public InstantiationException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param e previous exception
     */
    public InstantiationException(String message , Throwable e) {
        super(message , e);
    }

    /**
     * With previous exception
     * @param e previous exception
     */
    public InstantiationException(Throwable e) {
        super(e);
    }
}
