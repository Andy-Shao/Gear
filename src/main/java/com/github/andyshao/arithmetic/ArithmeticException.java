package com.github.andyshao.arithmetic;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 1, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class ArithmeticException extends RuntimeException {
    private static final long serialVersionUID = 4905425831762516882L;

    /**
     * without parameter
     */
    public ArithmeticException() {
        super();
    }

    /**
     * with error message
     * @param message the error message
     */
    public ArithmeticException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public ArithmeticException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * with previous exception
     * @param exception previous exception
     */
    public ArithmeticException(Throwable exception) {
        super(exception);
    }
}
