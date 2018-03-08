package com.github.andyshao.exception;

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
    private Result<?> result;

    public ResultException(Result<?> result) {
        super(result.description());
        this.result = result;
    }
    
    public ResultException(Result<?> result, Throwable e) {
        super(result.description(), e);
        this.result = result;
    }

    public Result<?> getResult() {
        return result;
    }
}
