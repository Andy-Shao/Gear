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
	double applyAsDouble(T value) throws Throwable;
	
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
	
	static <T> Convert<ExceptionableToDoubleFunction<T>, ToDoubleFunction<T>> toDoubleFunction() {
		return toDoubleFunction(RuntimeExceptionFactory.DEFAULT);
	}
}
