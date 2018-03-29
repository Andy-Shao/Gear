package com.github.andyshao.distribution.group;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 29, 2018<br>
 * Encoding: UNIX UTF-8
 * @author weichuang.shao
 *
 */
@SuppressWarnings("serial")
public class GroupManageException extends RuntimeException{
    public GroupManageException(String message) {
        super(message);
    }
    
    public GroupManageException(String message, Throwable exception) {
        super(message, exception);
    }
    
    public GroupManageException(Throwable exception) {
        super(exception);
    }
    
    public GroupManageException() {
    }
}
