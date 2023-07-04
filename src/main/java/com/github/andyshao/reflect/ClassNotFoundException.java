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
public class ClassNotFoundException extends ReflectiveOperationException {

    @Serial
    private static final long serialVersionUID = 564884292254566531L;

    /**
     * Not arg construction
     */
    public ClassNotFoundException() {
    }

    /**
     * with error message
     * @param message error message
     */
    public ClassNotFoundException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public ClassNotFoundException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * with previous exception
     * @param exception previous exception
     */
    public ClassNotFoundException(Throwable exception) {
        super(exception);
    }
}
