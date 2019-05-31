package com.github.andyshao.util.function;

import com.github.andyshao.util.stream.ThrowableException;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 30, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @param <U> argument type
 * @param <R> return type
 */
public interface ThrowableBiFunction<T, U, R> {
	R apply(T t, U u) throws Throwable;
	
	static <T, U, R> ExceptionableBiFunction<T, U, R> exceptionConvert(ThrowableBiFunction<T, U, R> function) {
		return new ExceptionableBiFunction<T, U, R>() {

			@Override
			public R apply(T t, U u) throws Exception {
				try {
					return function.apply(t, u);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			}
		};
	}
}
