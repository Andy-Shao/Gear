package com.github.andyshao.election;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 27, 2018<br>
 * Encoding: UNIX UTF-8
 * @author andy.shao
 *
 */
@SuppressWarnings("serial")
public class ElectionException extends RuntimeException {
    public ElectionException(String message) {
        super(message);
    }
    
    public ElectionException(String message, Throwable exception) {
        super(message, exception);
    }
    
    public ElectionException(Throwable exception) {
        super(exception);
    }
    
    public ElectionException() {
    }
}
