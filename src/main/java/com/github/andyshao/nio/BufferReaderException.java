package com.github.andyshao.nio;

import java.io.IOException;
import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript: The Exception for {@link BufferReader}<br>
 * Copyright: Copryright(c) Jun 21, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class BufferReaderException extends IOException {
    @Serial
    private static final long serialVersionUID = 571371713174119857L;

    /**
     * No arg construction
     */
    public BufferReaderException() {
    }

    /**
     * with error message
     * @param message error message
     */
    public BufferReaderException(String message) {
        super(message);
    }

    /**
     * with error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public BufferReaderException(String message , Throwable exception) {
        super(message , exception);
    }

    /**
     * with previous exception
     * @param exception previous exception
     */
    public BufferReaderException(Throwable exception) {
        super(exception);
    }
}
