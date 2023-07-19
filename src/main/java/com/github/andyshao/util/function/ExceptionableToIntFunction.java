package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

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
 */
public interface ExceptionableToIntFunction<T> {
	/**
	 * apply as int
	 * @param value input
	 * @return int value
	 * @throws Throwable any error
	 */
	int applyAsInt(T value) throws Throwable;

	/**
	 * to {@link ToIntFunction}
	 * @param f exception factory
	 * @return {@link ToIntFunction}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableToIntFunction<T>, ToIntFunction<T>> toIntFunction(RuntimeExceptionFactory<?> f) {
		return input -> {
			return t -> {
				try {
					return input.applyAsInt(t);
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to int function
	 * @return {@link ToIntFunction}
	 * @param <T> data type
	 */
	public static <T> Convert<ExceptionableToIntFunction<T>, ToIntFunction<T>> toIntFunction() {
		return toIntFunction(RuntimeExceptionFactory.DEFAULT);
	}
}
