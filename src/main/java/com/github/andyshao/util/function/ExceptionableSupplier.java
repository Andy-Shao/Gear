package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.function.Supplier;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 5, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <R> return type
 * @see Supplier
 */
@FunctionalInterface
public interface ExceptionableSupplier<R> {
	/**
	 * get operation
	 * @return the value
	 * @throws Throwable any error
	 */
	R get() throws Throwable;

	/**
	 * to supplier
	 * @param f exception factory
	 * @return {@link Supplier}
	 * @param <R> data type
	 */
	static <R> Convert<ExceptionableSupplier<R>, Supplier<R>> toSupplier(RuntimeExceptionFactory<?> f) {
		return input -> {
			return () -> {
				try {
					return input.get();
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to supplier
	 * @return {@link Supplier}
	 * @param <R> data type
	 */
	static <R> Convert<ExceptionableSupplier<R>, Supplier<R>> toSupplier() {
		return toSupplier(RuntimeExceptionFactory.DEFAULT);
	}
}
