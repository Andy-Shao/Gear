package com.github.andyshao.data.structure;

import java.io.Serial;

/**
 *
 */
public class HeapOperationException extends DataStructureException {
    @Serial
    private static final long serialVersionUID = 6700152770758060898L;

    /**
     * No arg construction
     */
    public HeapOperationException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public HeapOperationException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and previous exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public HeapOperationException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * With previous exception
     * @param cause previous exception
     */
    public HeapOperationException(Throwable cause) {
        super(cause);
    }
}
