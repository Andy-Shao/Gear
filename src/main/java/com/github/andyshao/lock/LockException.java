package com.github.andyshao.lock;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 18 Apr 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class LockException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -1686080993309076332L;

    /**
     * with error message
     * @param message error message
     */
    public LockException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public LockException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * previous exception
     * @param exception previous exception
     */
    public LockException(Throwable exception) {
        super(exception);
    }

    /**
     * No arg construction
     */
    public LockException() {
    }
}
