package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Aug 8, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
@SuppressWarnings("serial")
public class NotSupportConvertException extends RuntimeException {
    public NotSupportConvertException(String message) {
        super(message);
    }
    
    public NotSupportConvertException(Throwable ex) {
        super(ex);
    }
    
    public NotSupportConvertException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public NotSupportConvertException() {
    }
}
