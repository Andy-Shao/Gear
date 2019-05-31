package com.github.andyshao.util.function;

import com.github.andyshao.util.stream.ThrowableException;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @param <R> return type
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {
	R apply(T t) throws Throwable;

	static <T, R> ExceptionableFunction<T, R> exceptionConvert(ThrowableFunction<T, R> function) {
		return new ExceptionableFunction<T, R>() {
			@Override
			public R apply(T t) throws Exception {
				try {
					return function.apply(t);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			}
		};
	}
}
