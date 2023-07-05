package com.github.andyshao.exception;

import lombok.Getter;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 27, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
public class ResultException extends RuntimeException{
    private static final long serialVersionUID = 4909234766541027522L;
    @Getter
    private final Result<?> result;

    /**
     * With {@link Result}
     * @param result {@link Result}
     */
    public ResultException(Result<?> result) {
        super(result.description());
        this.result = result;
    }

    /**
     * With {@link Result} and previous exception
     * @param result {@link Result}
     * @param e previous exception
     */
    public ResultException(Result<?> result, Throwable e) {
        super(result.description(), e);
        this.result = result;
    }
}
