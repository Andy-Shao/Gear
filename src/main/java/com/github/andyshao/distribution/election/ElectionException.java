package com.github.andyshao.distribution.election;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
public class ElectionException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -469045035074290936L;

    /**
     * With error message
     * @param message error message
     */
    public ElectionException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public ElectionException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * With previous exception
     * @param exception previous exception
     */
    public ElectionException(Throwable exception) {
        super(exception);
    }

    /**
     * No arg construction
     */
    public ElectionException() {
    }
}
