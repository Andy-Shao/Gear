package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 28, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type one
 * @param <U> argument type two
 * @see BiConsumer
 */
@FunctionalInterface
public interface ExceptionableBiConsumer<T, U> {
	/**
	 * accept
	 * @param t left
	 * @param u right
	 * @throws Exception any error
	 */
	void accept(T t, U u) throws Exception;

	/**
	 * to {@link BiConsumer}
	 * @param factory {@link BiConsumer} factory
	 * @return {@link BiConsumer}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T,U> Convert<ExceptionableBiConsumer<T, U>, BiConsumer<T, U>> toBiConsumer(RuntimeExceptionFactory factory) {
		return input -> {
			return (t, u) -> {
				try {
					input.accept(t, u);
				} catch (Exception e) {
					throw factory.build(e);
				}
			};
		};
	}

	/**
	 * to {@link BiConsumer}
	 * @return {@link BiConsumer}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> Convert<ExceptionableBiConsumer<T, U>, BiConsumer<T, U>> toBiConsumer() {
		return toBiConsumer(RuntimeExceptionFactory.DEFAULT);
	}

	/**
	 * and then
	 * @param after after
	 * @return {@link ExceptionableBiConsumer}
	 */
	default ExceptionableBiConsumer<T, U> andThen(ExceptionableBiConsumer<? super T, ? super U> after) {
		Objects.requireNonNull(after);
		return (t, u) -> {
			this.accept(t, u);
			after.accept(t, u);
		};
	}
}
