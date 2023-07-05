package com.github.andyshao.distribution.group;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 29, 2018<br>
 * Encoding: UNIX UTF-8
 * @author weichuang.shao
 *
 */
public class GroupManageException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1937796700421894498L;

    /**
     * With error message
     * @param message error message
     */
    public GroupManageException(String message) {
        super(message);
    }

    /**
     * With error message and previous exception
     * @param message error message
     * @param exception previous exception
     */
    public GroupManageException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * With previous exception
     * @param exception previous exception
     */
    public GroupManageException(Throwable exception) {
        super(exception);
    }

    /**
     * No arg construction
     */
    public GroupManageException() {
    }
}
