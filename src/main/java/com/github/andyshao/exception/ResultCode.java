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
public enum ResultCode {
    SUCCESS("200", "Success"),
    ERROR("500", "Process has an error!");
    private String code;
    private String message;
    private ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
    public <DATA> Result<DATA> wrap(Result<DATA> result) {
        result.setCode(this.getCode());
        result.setMessage(this.getMessage());
        return result;
    }
}