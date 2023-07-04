package com.github.andyshao.arithmetic;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 1, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class SortException extends ArithmeticException {
    @Serial
    private static final long serialVersionUID = -6634240428324974480L;

    /**
     * No arg construction
     */
    public SortException() {
        super();
    }

    /**
     * With error message
     * @param message error message
     */
    public SortException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public SortException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * With previous exception
     * @param exception previous exception
     */
    public SortException(Throwable exception) {
        super(exception);
    }
}
