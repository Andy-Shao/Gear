package com.github.andyshao.util.stream;

import java.util.function.Function;

import com.github.andyshao.util.function.ExceptionableFunction;

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
 */
public final class StreamOperation {
	private StreamOperation() {}
	
	public static <T, R> Function<T, R> wrap(ExceptionableFunction<T, R> function, ExceptionFactory factory) {
		return t -> {
			try {
				return function.apply(t);
			} catch(Exception e) {
				throw factory.build(e);
			}
		};
	}
	
	public static <T, R> Function<T, R> wrap(ExceptionableFunction<T, R> function) {
		return wrap(function, e -> new RuntimeException(e));
	}
}
