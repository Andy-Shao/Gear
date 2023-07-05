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
public class TreeOperationException extends DataStructureException {
    @Serial
    private static final long serialVersionUID = 8831040862742000185L;

    /**
     * No arg construction
     */
    public TreeOperationException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public TreeOperationException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and previous exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public TreeOperationException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * Previous exception
     * @param cause previous exception
     */
    public TreeOperationException(Throwable cause) {
        super(cause);
    }
}
