package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

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
 */
public interface ExceptionableToDoubleFunction<T> {
	/**
	 * apply as {@link Double}
	 * @param value input
	 * @return double value
	 * @throws Throwable any error
	 */
	double applyAsDouble(T value) throws Throwable;

	/**
	 * to double function
	 * @param f exception function
	 * @return {@link ToDoubleFunction}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableToDoubleFunction<T>, ToDoubleFunction<T>> toDoubleFunction(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					return input.applyAsDouble(t);
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to double function
	 * @return {@link ToDoubleFunction}
 	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableToDoubleFunction<T>, ToDoubleFunction<T>> toDoubleFunction() {
		return toDoubleFunction(RuntimeExceptionFactory.DEFAULT);
	}
}
