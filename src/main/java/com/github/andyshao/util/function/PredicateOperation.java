package com.github.andyshao.util.function;

import java.util.function.Predicate;

import com.github.andyshao.lang.Convert;

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
	private PredicateOperation() {}
	
	public static final <T> Predicate<T> lambda(Predicate<T> predicate) {
		return predicate;
	}
	
	public static final <T> Predicate<T> negate(Predicate<T> predicate) {
		return predicate.negate();
	}
	
	public static final <T> Predicate<T> predicate(Convert<T, Boolean> convert) {
		return t -> convert.convert(t);
	}
}
