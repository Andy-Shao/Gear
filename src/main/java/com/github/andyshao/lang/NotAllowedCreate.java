package com.github.andyshao.lang;

/**
 * 
 * Title: For class construction which is not allowed<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 13, 2017<br>
 * Encoding:UNIX UTF-8
 * @author weichuang.shao
 *
 */
@SuppressWarnings("serial")
public class NotAllowedCreate extends RuntimeException {
    public NotAllowedCreate(Throwable e) {
        super(e);
    }
    
    public NotAllowedCreate(String message) {
        super(message);
    }
    
    public NotAllowedCreate(String message, Throwable e) {
        super(message, e);
    }
    
    public NotAllowedCreate() {
    }
}
