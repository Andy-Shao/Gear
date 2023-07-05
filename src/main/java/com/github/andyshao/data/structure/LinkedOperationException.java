package com.github.andyshao.data.structure;

import java.io.Serial;

/**
 * Linked Operation Exception
 */
public class LinkedOperationException extends DataStructureException {

    @Serial
    private static final long serialVersionUID = 3324113947275253394L;

    /**
     * No arg construction
     */
    public LinkedOperationException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public LinkedOperationException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and previous exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public LinkedOperationException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * With previous exception
     * @param cause previous exception
     */
    public LinkedOperationException(Throwable cause) {
        super(cause);
    }
}
