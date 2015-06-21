package com.github.andyshao.nio;

import java.io.IOException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 21, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class BufferReaderException extends IOException {
    private static final long serialVersionUID = 571371713174119857L;

    public BufferReaderException() {
    }

    public BufferReaderException(String message) {
        super(message);
    }

    public BufferReaderException(String message , Throwable exception) {
        super(message , exception);
    }

    public BufferReaderException(Throwable exception) {
        super(exception);
    }
}
