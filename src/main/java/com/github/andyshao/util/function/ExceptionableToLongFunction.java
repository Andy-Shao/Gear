package com.github.andyshao.util.function;

import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 14, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @see ToLongBiFunction
 */
public interface ExceptionableToLongFunction<T> {
	long applyAsLong(T value) throws Exception;
	
	static <T> Convert<ExceptionableToLongFunction<T>, ToLongFunction<T>> toLongFunction(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					return input.applyAsLong(t);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	static <T> Convert<ExceptionableToLongFunction<T>, ToLongFunction<T>> toLongFunction() {
		return toLongFunction(RuntimeExceptionFactory.DEFAULT);
	}
}
