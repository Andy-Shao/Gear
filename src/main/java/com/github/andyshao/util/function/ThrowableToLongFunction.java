package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.function.ToLongFunction;

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
 * @see ToLongFunction
 * @see ExceptionableToLongFunction
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableToLongFunction<T> {
	long applyAsLong(T value) throws Throwable;
	
	static <T> Convert<ThrowableToLongFunction<T>, ExceptionableToLongFunction<T>> toExceptionableToLongFunction() {
		return input -> {
			return t -> {
				try {
					return input.applyAsLong(t);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			};
		};
	}
}
