package com.github.andyshao.util.function;

import java.util.function.Predicate;

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
 * @see Predicate
 */
public interface ExceptionablePredicate<T> {
	boolean test(T t) throws Exception;
	
	static <T> Convert<ExceptionablePredicate<T>, Predicate<T>> toPredicate(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					return input.test(t);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	static <T> Convert<ExceptionablePredicate<T>, Predicate<T>> toPredicate() {
		return toPredicate(RuntimeExceptionFactory.DEFAULT);
	}
}
