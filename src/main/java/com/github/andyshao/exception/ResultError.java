package com.github.andyshao.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 23, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 *
 */
public class ResultError extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 4909234766541027522L;
    @Getter
    private final Result<?> result;

    /**
     * With {@link Result}
     * @param result {@link Result}
     */
    public ResultError(Result<?> result) {
        super(result.description());
        this.result = result;
    }

    /**
     * With {@link Result} and previous exception
     * @param result {@link Result}
     * @param e previous exception
     */
    public ResultError(Result<?> result, Throwable e) {
        super(result.description(), e);
        this.result = result;
    }
}
