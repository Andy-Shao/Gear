package com.github.andyshao.data.structure;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 12, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class TreeChildNodeNotEmptyException extends TreeOperationException {
    @Serial
    private static final long serialVersionUID = -3582916238481687068L;

    /**
     * No arg construction
     */
    public TreeChildNodeNotEmptyException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public TreeChildNodeNotEmptyException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and previous exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public TreeChildNodeNotEmptyException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * With previous exception
     * @param cause previous exception
     */
    public TreeChildNodeNotEmptyException(Throwable cause) {
        super(cause);
    }
}
