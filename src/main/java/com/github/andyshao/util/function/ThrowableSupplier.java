package com.github.andyshao.util.function;

import java.util.function.Supplier;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 * @see ExceptionableSupplier
 * @see Supplier
 */
public interface ThrowableSupplier<R> {
	R get() throws Throwable;
	
	static <R> Convert<ThrowableSupplier<R>, ExceptionableSupplier<R>> toExceptionableSupplier() {
		return input -> {
			return new ExceptionableSupplier<R>() {

				@Override
				public R get() throws Exception {
					try {
						return input.get();
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}
}
