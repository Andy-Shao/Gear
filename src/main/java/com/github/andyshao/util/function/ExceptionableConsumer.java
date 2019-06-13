package com.github.andyshao.util.function;

import java.util.function.Consumer;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

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
public interface ExceptionableConsumer<T> {
	void accept(T t) throws Exception;
	
	static <T> Convert<ExceptionableConsumer<T>, Consumer<T>> toConsumer(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					input.accept(t);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	static <T> Convert<ExceptionableConsumer<T>, Consumer<T>> toConsumer() {
		return toConsumer(RuntimeExceptionFactory.DEFAULT);
	}
}
