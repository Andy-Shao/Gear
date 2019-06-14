package com.github.andyshao.util.function;

import java.util.function.ToIntFunction;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 * @see ExceptionableToIntFunction
 */
public interface ThrowableToIntFunction<T> {
	int applyAsInt(T value) throws Throwable;
	
	static <T> Convert<ThrowableToIntFunction<T>, ExceptionableToIntFunction<T>> toExceptionableToIntFunction() {
		return input -> {
			return t -> {
				try {
					return input.applyAsInt(t);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			};
		};
	}
}