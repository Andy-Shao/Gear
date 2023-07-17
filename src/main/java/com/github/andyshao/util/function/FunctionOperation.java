package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;

import java.util.function.Function;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 1, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class FunctionOperation {
	private FunctionOperation() {
		throw new AssertionError("Does not support construction");
	}

	/**
	 * lambda
	 * @param function {@link Function}
	 * @return {@link Function}
	 * @param <T> left type
	 * @param <R> right type
	 */
	public static final <T, R> Function<T, R> lambda(Function<T, R> function) {
		return function;
	}

	/**
	 * function
	 * @param convert {@link Convert}
	 * @return {@link Function}
	 * @param <T> left type
	 * @param <R> right type
	 */
	public static final <T, R> Function<T, R> function(Convert<T, R> convert) {
		return convert::convert;
	}
}
