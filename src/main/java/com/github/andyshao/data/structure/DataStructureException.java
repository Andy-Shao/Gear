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
public class DataStructureException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 5831612224020640233L;

    /**
     * No arg construction
     */
    public DataStructureException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public DataStructureException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public DataStructureException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * With previous exception
     * @param cause previous exception
     */
    public DataStructureException(Throwable cause) {
        super(cause);
    }
}
