package com.github.andyshao.util.function;

import java.util.function.ToIntFunction;

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
 * @see ToIntFunction
 */
public interface ExceptionableToIntFunction<T> {
	int applyAsInt(T value) throws Exception;
	
	static <T> Convert<ExceptionableToIntFunction<T>, ToIntFunction<T>> toIntFunction(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					return input.applyAsInt(t);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	public static <T> Convert<ExceptionableToIntFunction<T>, ToIntFunction<T>> toIntFunction() {
		return toIntFunction(RuntimeExceptionFactory.DEFAULT);
	}
}
