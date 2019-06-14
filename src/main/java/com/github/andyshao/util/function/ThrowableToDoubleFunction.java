package com.github.andyshao.util.function;

import java.util.function.ToDoubleFunction;

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
 * @see ToDoubleFunction
 * @see ExceptionableToDoubleFunction
 */
public interface ThrowableToDoubleFunction<T> {
	double applyAsDouble(T value) throws Throwable;
	
	static <T> Convert<ThrowableToDoubleFunction<T>, ExceptionableToDoubleFunction<T>> toDoubleFunction() {
		return input -> {
			return t -> {
				try {
					return input.applyAsDouble(t);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			};
		};
	}
}
