package com.github.andyshao.util.function;

import java.util.function.Function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @param <R> return type
 */
@FunctionalInterface
public interface ExceptionableFunction<T, R> {
	R apply(T t) throws Exception;
	
	static <T, R> Convert<ExceptionableFunction<T, R>, Function<T, R>> toFunction(RuntimeExceptionFactory factory) {
		return input -> {
			return t -> {
				try {
					return input.apply(t);
				} catch (Exception e) {
					throw factory.build(e);
				}
			};
		};
	}
	
	static <T, R> Convert<ExceptionableFunction<T, R>, Function<T, R>> toFunction() {
		return toFunction(e -> new RuntimeException(e));
	}
}
