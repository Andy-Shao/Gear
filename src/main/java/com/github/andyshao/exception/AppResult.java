package com.github.andyshao.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Title: Application Result<br>
 * Descript: Add application code and application message base on {@link Result}<br>
 * Copyright: Copryright(c) Jul 18, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 * @see Result
 *
 */
public interface AppResult<T> extends Result<AppResult.DataNode<T>> {
	/**
	 * Get a success result without the data
	 * @return a success result
	 * @param <E> the type of data
	 */
	public static <E> AppResult<E> success() {
		return successData(null);
	}

	/**
	 * Get a success result with response code and message
	 * @param code response code
	 * @param message response message
	 * @return a success result
	 * @param <E> the type of data
	 */
	public static <E> AppResult<E> success(String code, String message) {
		return successData(null, ResultCodeEnum.SUCCESS, code, message);
	}

	/**
	 * Get a success result with response message
	 * @param message response message
	 * @return a success result
	 * @param <E> the type of data
	 */
	public static <E> AppResult<E> successMsg(String message) {
		ResultCode resultCode = ResultCodeEnum.SUCCESS;
		return successData(null, resultCode, resultCode.getCode(), message);
	}

	/**
	 * Get a success result with response code
	 * @param resultCode response code
	 * @return a success result
	 * @param <E> the type of data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> success(ResultCode resultCode) {
		return successData(null, resultCode, resultCode.getCode(), resultCode.getMessage());
	}

	/**
	 * Get a success result with response cod and message
	 * @param resultCode response code
	 * @param appMsg response message
	 * @return a success result
	 * @param <E> the type of data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> success(ResultCode resultCode, String appMsg){
		return successData(null, resultCode.getCode(), resultCode.getMessage(), resultCode.getCode(), appMsg);
	}

	/**
	 * Get a success result with response code, message and application code.
	 * @param resultCode response code
	 * @param appCode application code
	 * @param appMsg application message
	 * @return a success result
	 * @param <E> the type of data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> success(ResultCode resultCode, String appCode, String appMsg) {
		return successData(null, resultCode.getCode(), resultCode.getMessage(), appCode, appMsg);
	}

	/**
	 * Get a success result with a data
	 * @param data the response data
	 * @return a success result
	 * @param <E> the type of data
	 */
	public static <E> AppResult<E> successData(final E data) {
		return successData(data, ResultCodeEnum.SUCCESS);
	}

	/**
	 * Get a success result with a data and {@link ResultCode}
	 * @param data the result data
	 * @param resultCode the result code and message
	 * @return a success result
	 * @param <E> the type of the data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> successData(final E data, final ResultCode resultCode) {
		String code = resultCode.getCode();
		String message = resultCode.getMessage();
		return successData(data, code, message);
	}

	/**
	 * Get a success result with data, {@link ResultCode}, application code, and application message
	 * @param data the result data
	 * @param resultCode the result code and message
	 * @param appCode the application code
	 * @param appMsg the application message
	 * @return a success result
	 * @param <E> the type of the data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> successData(final E data, final ResultCode resultCode, String appCode, 
			String appMsg) {
		return successData(data, resultCode.getCode(), resultCode.getMessage(), appCode, appMsg);
	}

	/**
	 * Get a success result with data and response message
	 * @param data the result data
	 * @param message the result message
	 * @return a success result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> successData(final E data, String message) {
		return successData(data, ResultCodeEnum.SUCCESS.getCode(), message);
	}

	/**
	 * Get a success result with data and response code and message
	 * @param data the result data
	 * @param code the response code
	 * @param message the response message
	 * @return a success result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> successData(final E data, String code, String message) {
		String appCode = code;
		String appMessage = message;
		return successData(data, code, message, appCode, appMessage);
	}

	/**
	 * Get a success result with data, response code, response message, application code,
	 * and application message
	 * @param data the result data
	 * @param code the response code
	 * @param message the response message
	 * @param appCode the application code
	 * @param appMessage the application code
	 * @return a success result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> successData(final E data, String code, String message, String appCode,
			String appMessage) {
		return wrap(data, code, message, appCode, appMessage, true);
	}

	/**
	 * the error result which excludes the data
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> error() {
		return errorData(null);
	}

	/**
	 * Get an error result with error message
	 * @param msg error message
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> errorMsg(String msg) {
		return errorData(null, msg);
	}

	/**
	 * Get an error result with response code and message
	 * @param code response code
	 * @param msg response message
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> error(String code, String msg) {
		return errorData(null, ResultCodeEnum.ERROR, code, msg);
	}

	/**
	 * Get an error result with {@link ResultCode} and response message
	 * @param rc response code
	 * @param message response message
	 * @return an error result
	 * @param <E> the type of the data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> error(ResultCode rc, String message) {
		return errorData(null, rc, rc.getCode(), message);
	}

	/**
	 * Get an error result with {@link ResultCode}, application code and message
	 * @param rc response code and message
	 * @param code application code
	 * @param msg application message
	 * @return an error result
	 * @param <E> the type of the data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> error(ResultCode rc, String code, String msg) {
		return errorData(null, rc, code, msg);
	}

	/**
	 * Get an error result with data
	 * @param data the result data
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> errorData(final E data) {
		return errorData(data, ResultCodeEnum.ERROR);
	}

	/**
	 * Get an error result with data
	 * @param data the result data
	 * @param resultCode the response code and message
	 * @return an error result
	 * @param <E> the type of the data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> errorData(final E data, final ResultCode resultCode) {
		return errorData(data, resultCode, resultCode.getCode(), resultCode.getMessage());
	}

	/**
	 * Get an error result with data, {@link ResultCode}, application code and message
	 * @param data the result data
	 * @param resultCode the response code and message
	 * @param code the application code
	 * @param msg the application message
	 * @return an error result
 	 * @param <E> the type of the data
	 * @see ResultCode
	 */
	public static <E> AppResult<E> errorData(final E data, final ResultCode resultCode, String code, String msg) {
		return errorData(data, resultCode.getCode(), resultCode.getMessage(), code, msg);
	}

	/**
	 * Ge an error result with data and error message
	 * @param data the result data
	 * @param msg error message
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> errorData(final E data, String msg) {
		ResultCodeEnum rc = ResultCodeEnum.ERROR;
		return errorData(data, rc.getCode(), msg);
	}

	/**
	 * Get an error result with data, response code and message
	 * @param data the result data
	 * @param code the response code
	 * @param message the response message
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> errorData(final E data, String code, String message) {
		return errorData(data, code, message, code, message);
	}

	/**
	 * Get an error result with data, response code, response message, application code,
	 * and application message
	 * @param data the result data
	 * @param code the response code
	 * @param msg the response message
	 * @param appCode the application code
	 * @param appMsg the application message
	 * @return an error result
	 * @param <E> the type of the data
	 */
	public static <E> AppResult<E> errorData(final E data, String code, String msg, String appCode, String appMsg) {
		return wrap(data, code, msg, appCode, appMsg, false);
	}

	/**
	 * Get a {@link AppResult} with data, response code, response message,
	 * application code, and application message
	 * @param data the result data
	 * @param code response code
	 * @param message response message
	 * @param appCode application code
	 * @param appMessage application message
	 * @param isSuccess if the result is success then setting true
	 * @return a {@link AppResult}
	 * @param <E> the type of data
	 */
	public static <E> AppResult<E> wrap(final E data, String code, String message, String appCode, String appMessage,
			boolean isSuccess) {
		return new DefaultResult.Builder<E>()
				.code(code)
				.message(message)
				.isSuccess(isSuccess)
				.data(DataNode.<E>builder()
						.appErrorCode(appCode)
						.appErrorMsg(appMessage)
						.data(data)
						.build())
				.build();
	}

	/**
	 * the data node class for {@link AppResult}
	 * @param <T> the type of the node
	 */
	@Getter
	@Setter(value = AccessLevel.PROTECTED)
	public static class DataNode<T> {
		private String appErrorCode;
		private String appErrorMsg;
		private T data;

		/**
		 * {@link DataNodeBuilder}
		 * @return a {@link DataNodeBuilder}
		 * @param <E> the type of the data
		 */
		public static <E> DataNodeBuilder<E> builder() {
			return new DataNodeBuilder<E>();
		}
		
		static class DataNodeBuilder<E> {
			private String appErrorCode;
			private String appErrorMsg;
			private E data;
			
			public DataNodeBuilder<E> data(E data) {
				this.data = data;
				return this;
			}
			
			public DataNodeBuilder<E> appErrorMsg(String appErrorMsg) {
				this.appErrorMsg = appErrorMsg;
				return this;
			}
			
			public DataNodeBuilder<E> appErrorCode(String appErrorCode) {
				this.appErrorCode = appErrorCode;
				return this;
			}
			
			public DataNode<E> build() {
				DataNode<E> ret = new DataNode<E>();
				ret.setAppErrorCode(this.appErrorCode);
				ret.setAppErrorMsg(this.appErrorMsg);
				ret.setData(this.data);
				return ret;
			}
		}
	}

	/**
	 * The default initialization of {@link AppResult}
	 * @param <E> the type of result data
	 */
	static class DefaultResult<E> extends Result.DefaultResult<AppResult.DataNode<E>> implements AppResult<E> {
		private static final long serialVersionUID = 5733097731106369616L;
		
		static class Builder<T> {
			private AppResult.DataNode<T> data;
            private String message;
            private boolean isSuccess;
            private String code;
            
            public Builder<T> code(String code) {
            	this.code = code;
            	return this;
            }
            
            public Builder<T> isSuccess(boolean isSuccess) {
            	this.isSuccess = isSuccess;
            	return this;
            }
            
            public Builder<T> message(String message) {
            	this.message = message;
            	return this;
            }
            
            public Builder<T> data(AppResult.DataNode<T> data) {
            	this.data = data;
            	return this;
            }

			/**
			 * Build a {@link AppResult.DefaultResult}
			 * @return a {@link AppResult.DefaultResult}
			 */
			public AppResult.DefaultResult<T> build() {
				AppResult.DefaultResult<T> ret = new AppResult.DefaultResult<T>();
				ret.setData(this.data);
				ret.setMessage(this.message);
				ret.setCode(this.code);
				ret.isSuccess(this.isSuccess);
				return ret;
			}
			
		}
	}
}
