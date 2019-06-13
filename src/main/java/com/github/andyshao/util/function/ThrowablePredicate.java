package com.github.andyshao.util.function;

import java.util.function.Predicate;

import com.github.andyshao.lang.Convert;
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
 * @see ExceptionablePredicate
 * @see Predicate
 */
@FunctionalInterface
public interface ThrowablePredicate<T> {
	boolean test(T t) throws Throwable;
	
	static <T> Convert<ThrowablePredicate<T>, ExceptionablePredicate<T>> toExceptionablePredicate() {
		return input -> {
			return new ExceptionablePredicate<T>() {
			
				@Override
				public boolean test(T t) throws Exception {
					try {
						return input.test(t);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}
}
