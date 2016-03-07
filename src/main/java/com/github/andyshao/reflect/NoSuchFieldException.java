package com.github.andyshao.reflect;

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
    private static final long serialVersionUID = 7486693973906589683L;

    public NoSuchFieldException() {
    }

    public NoSuchFieldException(String message) {
        super(message);
    }

    public NoSuchFieldException(String message , Throwable exception) {
        super(message , exception);
    }

    public NoSuchFieldException(Throwable exception) {
        super(exception);
    }
}
