package com.github.andyshao.asm;

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
    private static final long serialVersionUID = -8385117238254124151L;

    public ASMOperationException() {
    }

    public ASMOperationException(String message) {
        super(message);
    }

    public ASMOperationException(String message , Throwable exception) {
        super(message , exception);
    }

    public ASMOperationException(Throwable exception) {
        super(exception);
    }
}
