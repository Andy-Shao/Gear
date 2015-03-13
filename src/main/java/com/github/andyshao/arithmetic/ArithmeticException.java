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

    public ArithmeticException() {
        super();
    }

    public ArithmeticException(String message) {
        super(message);
    }

    public ArithmeticException(String message , Throwable exception) {
        super(message , exception);
    }

    public ArithmeticException(Throwable exceptoin) {
        super(exceptoin);
    }
}
