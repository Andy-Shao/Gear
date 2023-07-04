package com.github.andyshao.arithmetic;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 16, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class GraphAlgException extends ArithmeticException {
    @Serial
    private static final long serialVersionUID = 6039554849054230704L;

    /**
     * No arg construction
     */
    public GraphAlgException() {
        super();
    }

    /**
     * With error message
     * @param message error message
     */
    public GraphAlgException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public GraphAlgException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * With previous exception
     * @param exception previous exception
     */
    public GraphAlgException(Throwable exception) {
        super(exception);
    }
}
