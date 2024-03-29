package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.function.ToDoubleFunction;

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
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableToDoubleFunction<T> {
	/**
	 * apply as double
	 * @param value input
	 * @return double value
	 * @throws Throwable any error
	 */
	double applyAsDouble(T value) throws Throwable;

	/**
	 * to {@link ExceptionableToDoubleFunction}
	 * @return {@link ExceptionableToDoubleFunction}
	 * @param <T> data type
	 */
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
