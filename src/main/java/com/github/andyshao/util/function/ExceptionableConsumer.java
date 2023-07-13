package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.Objects;
import java.util.function.Consumer;

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
	/**
	 * accept operation
	 * @param t input
	 * @throws Throwable any error
	 */
	void accept(T t) throws Throwable;

	/**
	 * to consumer
	 * @param f exception convert factory
	 * @return {@link Convert}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableConsumer<T>, Consumer<T>> toConsumer(RuntimeExceptionFactory f) {
		return input -> {
			return t -> {
				try {
					input.accept(t);
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to consumer
	 * @return {@link Convert}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableConsumer<T>, Consumer<T>> toConsumer() {
		return toConsumer(RuntimeExceptionFactory.DEFAULT);
	}

	/**
	 * and then
	 * @param after after {@link ExceptionableConsumer}
	 * @return new {@link ExceptionableConsumer}
	 */
	default ExceptionableConsumer<T> andThen(ExceptionableConsumer<? super T> after) {
		Objects.requireNonNull(after);
		return it -> {
			this.accept(it);
			after.accept(it);
		};
	}
}
