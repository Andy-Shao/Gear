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
public class SortException extends ArithmeticException {
    private static final long serialVersionUID = -6634240428324974480L;

    public SortException() {
        super();
    }

    public SortException(String message) {
        super(message);
    }

    public SortException(String message , Throwable exception) {
        super(message , exception);
    }

    public SortException(Throwable exceptoin) {
        super(exceptoin);
    }
}
