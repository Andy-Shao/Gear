package com.github.andyshao.data.structure;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 13, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class TreeIsEmptyException extends TreeOperationException {
    @Serial
    private static final long serialVersionUID = 7029818648399686287L;

    /**
     * No arg construction
     */
    public TreeIsEmptyException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public TreeIsEmptyException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and previous exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public TreeIsEmptyException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * Previous exception
     * @param cause previous exception
     */
    public TreeIsEmptyException(Throwable cause) {
        super(cause);
    }
}
