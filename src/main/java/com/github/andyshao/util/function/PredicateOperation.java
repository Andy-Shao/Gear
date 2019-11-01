package com.github.andyshao.util.function;

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
	private PredicateOperation() {}
	
	public static final <T> Predicate<T> lambda(Predicate<T> predicate) {
		return predicate;
	}
}
