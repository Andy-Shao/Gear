package com.github.andyshao.util.function;

import java.util.function.Function;

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
public final class FunctionOperation {
	private FunctionOperation() {}
	
	public static final <T, R> Function<T, R> lambda(Function<T, R> function) {
		return function;
	}
	
	public static final <T, R> Function<T, R> function(Convert<T, R> convert) {
		return t -> convert.convert(t);
	}
}
