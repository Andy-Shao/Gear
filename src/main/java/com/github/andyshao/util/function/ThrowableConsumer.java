package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 * @see ExceptionableConsumer
 * @see Consumer
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
@FunctionalInterface
public interface ThrowableConsumer<T> {
	/**
	 * accept operation
	 * @param t input
	 * @throws Throwable any error
	 */
	void accept(T t) throws Throwable;

	/**
	 * to {@link ExceptionableConsumer}
	 * @return {@link ExceptionableConsumer}
	 * @param <T> data type
	 */
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

	/**
	 * and then
	 * @param after after {@link ThrowableConsumer}
	 * @return {@link ThrowableConsumer}
	 */
	default ThrowableConsumer<T> andThen(ThrowableConsumer<? super T> after) {
		Objects.requireNonNull(after);
		return it -> {
			this.accept(it);
			after.accept(it);
		};
	}
}
