package com.github.andyshao.data.structure;

import java.io.Serial;

/**
 * Graph Operation Exception
 */
public class GraphOperationException extends DataStructureException {

    @Serial
    private static final long serialVersionUID = -6437250465369070393L;

    /**
     * No Arg Construction
     */
    public GraphOperationException() {
        super();
    }

    /**
     * With error message
     * @param errorMsg error message
     */
    public GraphOperationException(String errorMsg) {
        super(errorMsg);
    }

    /**
     * With error message and previous exception
     * @param errorMsg error message
     * @param cause previous exception
     */
    public GraphOperationException(String errorMsg , Throwable cause) {
        super(errorMsg , cause);
    }

    /**
     * WIth previous exception
     * @param cause previous exception
     */
    public GraphOperationException(Throwable cause) {
        super(cause);
    }
}
