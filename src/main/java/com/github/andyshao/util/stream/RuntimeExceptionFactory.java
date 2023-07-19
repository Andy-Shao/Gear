package com.github.andyshao.util.stream;

/**
 *
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 * @param <T> exception type
 *
 */
@FunctionalInterface
public interface RuntimeExceptionFactory<T extends RuntimeException> {
	/**default factory*/
	static RuntimeExceptionFactory<? extends RuntimeException> DEFAULT = RuntimeException::new;

	/**
	 * exception factory
	 * @param e previous exception
	 * @return a new exception
	 */
	T build(Throwable e);
}
