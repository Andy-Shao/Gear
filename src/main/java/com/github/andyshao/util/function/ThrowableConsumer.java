package com.github.andyshao.util.function;

import com.github.andyshao.util.stream.ThrowableException;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 */
@FunctionalInterface
public interface ThrowableConsumer<T> {
	void accept(T t) throws Throwable;
	
	static <T> ExceptionableConsumer<T> exceptionConvert(ThrowableConsumer<T> consumer) {
		return new ExceptionableConsumer<T>() {
			@Override
			public void accept(T t) throws Exception {
				try {
					consumer.accept(t);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			}

		};
	}
}
