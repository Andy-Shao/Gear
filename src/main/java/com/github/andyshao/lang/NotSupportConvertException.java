package com.github.andyshao.lang;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Aug 8, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class NotSupportConvertException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3695473346997477861L;

    /**
     * With error message
     * @param message error message
     */
    public NotSupportConvertException(String message) {
        super(message);
    }

    /**
     * with previous exception
     * @param ex previous exception
     */
    public NotSupportConvertException(Throwable ex) {
        super(ex);
    }

    /**
     * with error message and previous exception
     * @param message the error message
     * @param ex the previous exception
     */
    public NotSupportConvertException(String message, Throwable ex) {
        super(message, ex);
    }

    /**
     * No arg construction
     */
    public NotSupportConvertException() {
    }
}
