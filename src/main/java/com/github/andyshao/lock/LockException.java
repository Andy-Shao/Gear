package com.github.andyshao.lock;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 18 Apr 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
@SuppressWarnings("serial")
public class LockException extends RuntimeException {
    public LockException(String message) {
        super(message);
    }
    
    public LockException(String message, Throwable exception) {
        super(message, exception);
    }
    
    public LockException(Throwable exception) {
        super(exception);
    }
    
    public LockException() {
    }
}
