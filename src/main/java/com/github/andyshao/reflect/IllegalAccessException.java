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
public class IllegalAccessException extends ReflectiveOperationException {

    private static final long serialVersionUID = 7602834395539260320L;

    public IllegalAccessException() {
    }

    public IllegalAccessException(String message) {
        super(message);
    }

    public IllegalAccessException(String message , Throwable e) {
        super(message , e);
    }

    public IllegalAccessException(Throwable e) {
        super(e);
    }
}
