package com.github.andyshao.lang.number;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class ByteSizeOverLoadException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -9061320579451877325L;

    /**
     * With error message
     * @param message error message
     */
    public ByteSizeOverLoadException(String message) {
        super(message);
    }

    /**
     * With previous exception
     * @param e previous exception
     */
    public ByteSizeOverLoadException(Throwable e) {
        super(e);
    }

    /**
     * WIth error message and previous exception
     * @param message error message
     * @param e previous exception
     */
    public ByteSizeOverLoadException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * No arg construction
     */
    public ByteSizeOverLoadException() {
    }
}
