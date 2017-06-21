package com.github.andyshao.lang.number;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class ByteSizeOverException extends RuntimeException {
    private static final long serialVersionUID = -9061320579451877325L;
    public ByteSizeOverException(String message) {
        super(message);
    }
    public ByteSizeOverException(Throwable e) {
        super(e);
    }
    public ByteSizeOverException(String message, Throwable e) {
        super(message, e);
    }
    public ByteSizeOverException() {
    }
}
