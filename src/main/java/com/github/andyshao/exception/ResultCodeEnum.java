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
public enum ResultCodeEnum implements ResultCode {
    SUCCESS("200", "Success"),
    ERROR("500", "Process has an error!");
    private String code;
    private String message;
    private ResultCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getMessage() {
        return this.message;
    }
    public <DATA> Result<DATA> wrap(Result<DATA> result) {
        result.setCode(this.getCode());
        result.setMessage(this.getMessage());
        return result;
    }
}