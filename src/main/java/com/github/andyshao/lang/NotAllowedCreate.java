package com.github.andyshao.lang;

import java.io.Serial;

/**
 * 
 * Title: For class construction which is not allowed<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 13, 2017<br>
 * Encoding:UNIX UTF-8
 * @author weichuang.shao
 *
 */
public class NotAllowedCreate extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7807332386517818076L;

    /**
     * with previous exception
     * @param e previous exception
     */
    public NotAllowedCreate(Throwable e) {
        super(e);
    }

    /**
     * with error message
     * @param message error message
     */
    public NotAllowedCreate(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param e previous exception
     */
    public NotAllowedCreate(String message, Throwable e) {
        super(message, e);
    }

    /**
     * No arg construction
     */
    public NotAllowedCreate() {
    }
}
