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
    /**
     * get data
     * @return data
     */
    DATA getData();

    /**
     * set data
     * @param data data
     */
    void setData(DATA data);

    /**
     * get message
     * @return message
     */
    String getMessage();

    /**
     * set message
     * @param message message
     */
    void setMessage(String message);

    /**
     * is success
     * @return if it is then true
     */
    boolean isSuccess();

    /**
     * set success tage
     * @param isSuccess success tage
     */
    void isSuccess(boolean isSuccess);

    /**
     * get code
     * @return code value
     */
    String getCode();

    /**
     * set code
     * @param code code value
     */
    void setCode(String code);

    /**
     * the description of {@link Result}
     * @return the description
     */
    default String description() {
        StringBuilder sb = new StringBuilder();
        sb.append("isSuccess = ").append(this.isSuccess());
        sb.append(", message = ").append(this.getMessage());
        return sb.toString();
    }

    /**
     * Get a success result
     * @param resultCode response code and message
     * @return a success result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> success(ResultCode resultCode) {
        return success(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * Get a success result with response code and message
     * @param resultCode response code
     * @param message response message
     * @return a success result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> success(ResultCode resultCode, String message) {
        return success(resultCode.getCode() , message);
    }

    /**
     * Get a success result with response code and message
     * @param code response code
     * @param message response message
     * @return a success result
     * @param <E> the type of the data
     */
    static <E> Result<E> success(String code, String message) {
        return Result.<E>wrap(null, code, message, true);
    }

    /**
     * Get a success result with data
     * @param data the result data
     * @return a success result
     * @param <E> the type of the data
     */
    static <E> Result<E> successData(E data){
        return successData(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * Get a success result with data, response code and message
     * @param data the result data
     * @param resultCode the response code and message
     * @return a success result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> successData(E data, ResultCode resultCode) {
        return success(data, resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * Get a success result with data, response code and message
     * @param data the result data
     * @param resultCode the response code
     * @param message the response message
     * @return a success result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> successData(E data, ResultCode resultCode, String message) {
        return success(data , resultCode.getCode() , message);
    }

    /**
     * Get a success result with data and response message
     * @param data the result data
     * @param message the response message
     * @return a success result
     * @param <E> the type of the data
     */
    static <E> Result<E> successData(E data, String message){
        return success(data, ResultCodeEnum.SUCCESS.getCode(), message);
    }

    /**
     * Get a success result with data, response code and message
     * @param data the result data
     * @param code the response code
     * @param message the response message
     * @return a success result
     * @param <E> the type of the data
     */
    static <E> Result<E> success(E data, String code, String message) {
        return Result.<E>wrap(data, code, message, true);
    }

    /**
     * Get a success result without data
     * @return a success result
     * @param <E> the type of the data
     */
    static <E> Result<E> success() {
        return Result.<E>success(ResultCodeEnum.SUCCESS);
    }

    /**
     * Get a success result with response message
     * @param message response message
     * @return a success result
     * @param <E> the type of the data
     */
    static <E> Result<E> successMsg(String message) {
        return success(ResultCodeEnum.SUCCESS.getCode(), message);
    }

    /**
     * Ge an error result with {@link ResultCode}
     * @param resultCode the response code and message
     * @return an error result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> error(ResultCode resultCode) {
        return Result.<E>error(resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * Get an error result with response code and message
     * @param resultCode the response code
     * @param message the response message
     * @return an error result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> error(ResultCode resultCode, String message) {
        return error(resultCode.getCode() , message);
    }

    /**
     * Get an error result without data
     * @return an error result
     * @param <E> the type of the data
     */
    static <E> Result<E> error() {
        return Result.<E>error(ResultCodeEnum.ERROR);
    }

    /**
     * Get an error result with response message
     * @param message an error message
     * @return an error result
     * @param <E> the type of the data
     */
    static <E> Result<E> errorMsg(String message) {
        return Result.<E>error(ResultCodeEnum.ERROR.getCode(), message);
    }

    /**
     * Get an error result with response code and message
     * @param code the response code
     * @param message the response message
     * @return an error result
     * @param <E> the type of the data
     */
    static <E> Result<E> error(String code, String message) {
        return Result.<E>error(null, code, message);
    }

    /**
     * Get an error result with data
     * @param data the result data
     * @return an error result
     * @param <E> the type of the data
     */
    static <E> Result<E> errorData(E data) {
        return Result.<E>errorData(data , ResultCodeEnum.ERROR.getMessage());
    }

    /**
     * Get an error result with data and response message
     * @param data the result data
     * @param message the response message
     * @return an error result
     * @param <E> the type of the data
     */
    static <E> Result<E> errorData(E data, String message) {
        return Result.<E>error(data, ResultCodeEnum.ERROR.getCode(), message);
    }

    /**
     * Get an error result with data, response code and message
     * @param data the result data
     * @param resultCode the response code and message
     * @return an error result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> errorData(E data, ResultCode resultCode) {
        return error(data, resultCode.getCode(), resultCode.getMessage());
    }

    /**
     * Get an error result with data, response code and message
     * @param data the result data
     * @param resultCode the response code
     * @param message the response message
     * @return an error result
     * @param <E> the type of the data
     * @see ResultCode
     */
    static <E> Result<E> errorData(E data, ResultCode resultCode, String message) {
        return error(data, resultCode.getCode(), message);
    }

    /**
     * Get an error result with data, response code and message
     * @param data the result data
     * @param code the response code
     * @param message the response message
     * @return an error result
     * @param <E> the type of the data
     */
    static <E> Result<E> error(E data, String code, String message) {
        return Result.<E>wrap(data , code , message , false);
    }

    /**
     * Get a {@link Result} with data, response code and message
     * @param data the result data
     * @param code the response code
     * @param message the response message
     * @param isSuccess if the response is success then setting true
     * @return a {@link Result}
     * @param <E> the type of the data
     */
    static <E> Result<E> wrap(E data, String code, String message, boolean isSuccess) {
        Result<E> result = new DefaultResult<>();
        result.setData(data);
        result.isSuccess(isSuccess);
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    /**
     * default result
     * @param <E> data type
     */
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

        /**
         * builder
         * @return {@link SelfBuilder}
         * @param <T> data type
         */
        public static <T> SelfBuilder<T> builder() {
        	return new SelfBuilder<T>();
        }

        /**
         * self builder
         * @param <T> data type
         */
        static class SelfBuilder<T> {
        	private T data;
            private String message;
            private boolean isSuccess;
            private String code;

            /**
             * set code
             * @param code code
             * @return {@link SelfBuilder}
             */
            public SelfBuilder<T> code(String code) {
            	this.code = code;
            	return this;
            }

            /**
             * set success tage
             * @param isSuccess success tage
             * @return {@link SelfBuilder}
             */
            public SelfBuilder<T> isSuccess(boolean isSuccess) {
            	this.isSuccess = isSuccess;
            	return this;
            }

            /**
             * set message
             * @param message message
             * @return {@link SelfBuilder}
             */
            public SelfBuilder<T> message(String message) {
            	this.message = message;
            	return this;
            }

            /**
             * set data
             * @param data data
             * @return {@link SelfBuilder}
             */
            public SelfBuilder<T> data(T data) {
            	this.data = data;
            	return this;
            }

            /**
             * build action
             * @return {@link DefaultResult}
             */
            public DefaultResult<T> build() {
            	DefaultResult<T> ret = new DefaultResult<T>();
            	ret.data = this.data;
            	ret.message = this.message;
            	ret.isSuccess = this.isSuccess;
            	ret.code = this.code;
				return ret;
            }
        }
    }
}