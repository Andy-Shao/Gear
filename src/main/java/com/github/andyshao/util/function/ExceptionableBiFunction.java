package com.github.andyshao.util.function;

import java.util.function.BiFunction;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 28, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type one
 * @param <U> argument type two
 * @param <R> return type
 */
public interface ExceptionableBiFunction<T, U, R> {
	R apply(T t, U u) throws Exception;
	
	static <T, U, R> Convert<ExceptionableBiFunction<T, U, R>, BiFunction<T, U, R>> toBiFunction(RuntimeExceptionFactory f) {
		return input -> {
			return (t, u) -> {
				try {
					return input.apply(t, u);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	static <T, U, R> Convert<ExceptionableBiFunction<T, U, R>, BiFunction<T, U, R>> toBiFunction() {
		return toBiFunction(e -> new RuntimeException(e));
	}
}
