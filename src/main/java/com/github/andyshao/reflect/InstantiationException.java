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
public class InstantiationException extends ReflectiveOperationException {

    private static final long serialVersionUID = -1040253531882507911L;

    public InstantiationException() {
    }

    public InstantiationException(String message) {
        super(message);
    }

    public InstantiationException(String message , Throwable e) {
        super(message , e);
    }

    public InstantiationException(Throwable e) {
        super(e);
    }
}
