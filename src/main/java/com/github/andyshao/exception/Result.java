package com.github.andyshao.exception;

import java.io.Serializable;

/**
 * 
 * Title: Servlet Result Definition<br>
 * Descript:<br>
 * Copyright: Copryright(c) Sep 27, 2017<br>
 * Encoding:UNIX UTF-8
 * @author andy.shao
 * @param <DATA> data type
 */
public interface Result<DATA> extends Serializable{
    DATA getData();
    void setData(DATA data);
    String getMessage();
    void setMessage(String message);
    boolean isSuccess();
    void isSuccess(boolean isSuccess);
    String getCode();
    void setCode(String code);
    
    default String description() {
        StringBuilder sb = new StringBuilder();
        sb.append("isSuccess = ").append(this.isSuccess());
        sb.append(", message = ").append(this.getMessage());
        return sb.toString();
    }
    
    static <E> Result<E> success(ResultCode resultCode) {
        return Result.<E>wrap(null , resultCode.getCode() , resultCode.getMessage() , true);
    }
    
    static <E> Result<E> success(E data){
        return Result.<E>wrap(data, ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), true);
    }
    
    
    static <E> Result<E> success() {
        return Result.<E>success(ResultCode.SUCCESS);
    }
    
    static <E> Result<E> error(ResultCode resultCode) {
        return Result.<E>error(resultCode.getCode(), resultCode.getMessage());
    }
    
    static <E> Result<E> error() {
        return Result.<E>error(ResultCode.ERROR);
    }
    
    static <E> Result<E> errorMsg(String message) {
        return Result.<E>error(ResultCode.ERROR.getCode(), message);
    }
    
    static <E> Result<E> error(String code, String message) {
        return Result.<E>error(null, code, message);
    }
    
    static <E> Result<E> error(E data) {
        return Result.<E>error(data , ResultCode.ERROR.getMessage());
    }
    
    static <E> Result<E> error(E data, String message) {
        return Result.<E>error(data, ResultCode.ERROR.getCode(), message);
    }
    
    static <E> Result<E> error(E data, String code, String message) {
        return Result.<E>wrap(data , code , message , false);
    }
    
    static <E> Result<E> wrap(E data, String code, String message, boolean isSuccess) {
        Result<E> result = new DefaultResult<>();
        result.setData(data);
        result.isSuccess(isSuccess);
        result.setMessage(message);
        result.setCode(code);
        return result;
    }
    
    static class DefaultResult<E> implements Result<E>{
        private static final long serialVersionUID = -8009738064238682676L;
        private E data;
        private String message;
        private boolean isSuccess;
        private String code;

        @Override
        public E getData() {
            return this.data;
        }

        @Override
        public void setData(E data) {
            this.data = data;
        }

        @Override
        public String getMessage() {
            return this.message;
        }

        @Override
        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public boolean isSuccess() {
            return this.isSuccess;
        }

        @Override
        public void isSuccess(boolean isSuccess) {
            this.isSuccess = isSuccess;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public void setCode(String code) {
            this.code = code;
        }
    }
}