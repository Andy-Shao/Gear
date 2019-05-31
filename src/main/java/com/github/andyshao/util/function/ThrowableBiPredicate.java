package com.github.andyshao.util.function;

import com.github.andyshao.util.stream.ThrowableException;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 30, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @param <U> argujent type
 */
public interface ThrowableBiPredicate<T, U> {
	boolean test(T t, U u) throws Throwable;
	
	static <T, U> ExceptionableBiPredicate<T, U> exceptionCovert(ThrowableBiPredicate<T, U> predicate) {
		return new ExceptionableBiPredicate<T, U>() {

			@Override
			public boolean test(T t, U u) throws Exception {
				try {
					return predicate.test(t, u);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			}
		};
	}
}
