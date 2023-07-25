package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;

import java.util.function.Predicate;

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
public final class PredicateOperation {
	private PredicateOperation() {
		throw new AssertionError("Does not support construction");
	}

	/**
	 * lambda
	 * @param predicate {@link Predicate}
	 * @return origin {@link Predicate}
	 * @param <T> data type
	 */
	public static final <T> Predicate<T> lambda(Predicate<T> predicate) {
		return predicate;
	}

	/**
	 * negate operation
	 * @param predicate {@link Predicate}
	 * @return negate {@link Predicate}
	 * @param <T> data type
	 */
	public static final <T> Predicate<T> negate(Predicate<T> predicate) {
		return predicate.negate();
	}

	/**
	 * {@link Convert} to {@link Predicate}
	 * @param convert {@link Convert}
	 * @return {@link Predicate}
	 * @param <T> data type
	 */
	public static final <T> Predicate<T> predicate(Convert<T, Boolean> convert) {
		return t -> convert.convert(t);
	}
}
