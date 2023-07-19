package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.Objects;
import java.util.function.Function;

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
 * @param <R> return type
 * @see ExceptionableFunction
 * @see Function
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
@FunctionalInterface
public interface ThrowableFunction<T, R> {
	/**
	 * apply operation
	 * @param t input
	 * @return ret
	 * @throws Throwable any error
	 */
	R apply(T t) throws Throwable;

	/**
	 * to {@link ExceptionableConsumer}
	 * @return {@link ExceptionableConsumer}
	 * @param <T> data type
	 * @param <R> return type
	 */
	static <T, R> Convert<ThrowableFunction<T, R>, ExceptionableFunction<T, R>> toExceptionableFunction() {
		return input -> {
			return new ExceptionableFunction<T, R>() {
				@Override
				public R apply(T t) throws Exception {
					try {
						return input.apply(t);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}

	/**
	 * compose operation
	 * @param before before {@link ThrowableFunction}
	 * @return a new {@link ThrowableFunction}
	 * @param <V> data type
	 */
	default <V> ThrowableFunction<V, R> compose(ThrowableFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return v -> this.apply(before.apply(v));
	}

	/**
	 * and then
	 * @param after after {@link ThrowableFunction}
	 * @return a new {@link Throwable}
	 * @param <V> data type
	 */
	default <V> ThrowableFunction<T, V> andThen(ThrowableFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return t -> after.apply(this.apply(t));
	}

	/**
	 * identity
	 * @return {@link ThrowableFunction}
	 * @param <T> data type
	 */
	static <T> ThrowableFunction<T, T> identity() {
		return t -> t;
	}
}
