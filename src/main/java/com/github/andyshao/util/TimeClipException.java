package com.github.andyshao.util;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class TimeClipException extends RuntimeException {
    private static final long serialVersionUID = -9160280797376574925L;
    public TimeClipException(String message) {
        super(message);
    }
    public TimeClipException(Throwable e) {
        super(e);
    }
    public TimeClipException(String message, Throwable e) {
        super(message, e);
    }
    public TimeClipException() {
    }
}
