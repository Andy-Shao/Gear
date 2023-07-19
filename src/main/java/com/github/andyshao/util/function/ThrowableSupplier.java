package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 * @see ExceptionableSupplier
 * @see Supplier
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableSupplier<R> {
	/**
	 * get operation
	 * @return ret
	 * @throws Throwable any error
	 */
	R get() throws Throwable;

	/**
	 * to {@link ExceptionableSupplier}
	 * @return {@link ExceptionableSupplier}
	 * @param <R> return type
	 */
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
