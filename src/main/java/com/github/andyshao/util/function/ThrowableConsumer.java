package com.github.andyshao.util.function;

import java.util.function.Consumer;

import com.github.andyshao.lang.Convert;
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
 * @see ExceptionableConsumer
 * @see Consumer
 */
@FunctionalInterface
public interface ThrowableConsumer<T> {
	void accept(T t) throws Throwable;
	
	static <T> Convert<ThrowableConsumer<T>, ExceptionableConsumer<T>> toExceptionableConsumer() {
		return input -> {
			return new ExceptionableConsumer<T>() {
				@Override
				public void accept(T t) throws Exception {
					try {
						input.accept(t);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			
			};
		};
	}
}
