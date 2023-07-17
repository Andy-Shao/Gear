package com.github.andyshao.util.function;

import java.util.function.Supplier;

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
public class SupplierOperation {
	private SupplierOperation() {
		throw new AssertionError("Does not support exception");
	}

	/**
	 * lambda
	 * @param supplier {@link Supplier}
	 * @return original {@link Supplier}
	 * @param <T> data type
	 */
	public static final <T> Supplier<T> lambda(Supplier<T> supplier) {
		return supplier;
	}
}
