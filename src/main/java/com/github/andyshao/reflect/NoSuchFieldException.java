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
public class NoSuchFieldException extends ReflectiveOperationException {
    @Serial
    private static final long serialVersionUID = 7486693973906589683L;

    /**
     * No arg Exception
     */
    public NoSuchFieldException() {
    }

    /**
     * With error message
     * @param message error message
     */
    public NoSuchFieldException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public NoSuchFieldException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * With previous exception
     * @param exception previous exception
     */
    public NoSuchFieldException(Throwable exception) {
        super(exception);
    }
}
