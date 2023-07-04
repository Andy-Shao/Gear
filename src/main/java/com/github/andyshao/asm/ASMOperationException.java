package com.github.andyshao.asm;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 6, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class ASMOperationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8385117238254124151L;

    /**
     * No parameter constructor
     */
    public ASMOperationException() {
    }

    /**
     * with error message
     * @param message error message
     */
    public ASMOperationException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public ASMOperationException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * with previous exception
     * @param exception previous exception
     */
    public ASMOperationException(Throwable exception) {
        super(exception);
    }
}
