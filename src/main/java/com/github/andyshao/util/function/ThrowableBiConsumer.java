package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 30, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @param <U> artument type
 * @see ExceptionableBiConsumer
 * @see BiConsumer
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableBiConsumer<T, U> {
	/**
	 * accept operation
	 * @param t left type
	 * @param u right type
	 * @throws Throwable any error
	 */
	void accept(T t, U u) throws Throwable;

	/**
	 * to {@link ExceptionableBiConsumer}
	 * @return {@link ExceptionableBiConsumer}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> Convert<ThrowableBiConsumer<T, U>, ExceptionableBiConsumer<T, U>> toExceptionableBiConsumer() {
		return input -> {
			return new ExceptionableBiConsumer<T, U>() {
				@Override
				public void accept(T t, U u) throws Exception {
					try {
						input.accept(t, u);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}

	/**
	 * and then
	 * @param after after operation
	 * @return {@link ThrowableBiConsumer}
	 */
	default ThrowableBiConsumer<T, U> andThen(ThrowableBiConsumer<? super T, ? super U> after) {
		Objects.requireNonNull(after);
		return (t, u) -> {
			this.accept(t, u);
			after.accept(t, u);
		};
	}
}
