package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.function.ToIntFunction;

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
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableToIntFunction<T> {
	/**
	 * apply as int
	 * @param value input
	 * @return int value
	 * @throws Throwable any error
	 */
	int applyAsInt(T value) throws Throwable;

	/**
	 * to {@link ExceptionableToIntFunction}
	 * @return {@link ExceptionableToIntFunction}
	 * @param <T> data type
	 */
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