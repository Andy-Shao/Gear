package com.github.andyshao.util.function;

import java.util.function.ToDoubleFunction;

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
 * @see ToDoubleFunction
 */
public interface ExceptionableToDoubleFunction<T> {
	double applyAsDouble(T value) throws Exception;
	
	static <T> Convert<ExceptionableToDoubleFunction<T>, ToDoubleFunction<T>> toDoubleFunction(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					return input.applyAsDouble(t);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	static <T> Convert<ExceptionableToDoubleFunction<T>, ToDoubleFunction<T>> toDoubleFunction() {
		return toDoubleFunction(RuntimeExceptionFactory.DEFAULT);
	}
}
