package com.github.andyshao.arithmetic;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 16, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class GraphAlgException extends ArithmeticException {
    private static final long serialVersionUID = 6039554849054230704L;

    public GraphAlgException() {
        super();
    }

    public GraphAlgException(String message) {
        super(message);
    }

    public GraphAlgException(String message , Throwable exception) {
        super(message , exception);
    }

    public GraphAlgException(Throwable exceptoin) {
        super(exceptoin);
    }
}
