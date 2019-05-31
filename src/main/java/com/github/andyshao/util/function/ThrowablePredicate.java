package com.github.andyshao.util.function;

import com.github.andyshao.util.stream.ThrowableException;

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
 */
@FunctionalInterface
public interface ThrowablePredicate<T> {
	boolean test(T t) throws Throwable;
	
	static <T> ExceptionablePredicate<T> exceptionConvert(ThrowablePredicate<T> predicate) {
		return new ExceptionablePredicate<T>() {

			@Override
			public boolean test(T t) throws Exception {
				try {
					return predicate.test(t);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			}
		};
	}
}
