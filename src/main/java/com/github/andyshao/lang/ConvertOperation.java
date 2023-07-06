package com.github.andyshao.lang;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ConvertOperation {
	private ConvertOperation() {}

	/**
	 * convert operation
	 * @param function operation
	 * @return output
	 * @param <T> input type
	 * @param <R> output type
	 */
	public static final <T, R> Convert<T, R> convert(Function<T, R> function) {
		return in -> function.apply(in);
	}

	/**
	 * convert operation
	 * @param predicate operation
	 * @return output
	 * @param <T> input type
	 */
	public static final <T> Convert<T, Boolean> convert(Predicate<T> predicate) {
		return in -> predicate.test(in);
	}
}
