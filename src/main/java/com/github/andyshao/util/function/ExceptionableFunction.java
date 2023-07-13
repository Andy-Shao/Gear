package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

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
 * @see Function
 */
@FunctionalInterface
public interface ExceptionableFunction<T, R> {
	/**
	 * apply
	 * @param t argument
	 * @return ret
	 * @throws Throwable any error
	 */
	R apply(T t) throws Throwable;

	/**
	 * to function
	 * @param factory exception convert factory
	 * @return {@link Convert}
	 * @param <T> left type
	 * @param <R> right type
	 */
	static <T, R> Convert<ExceptionableFunction<T, R>, Function<T, R>> toFunction(RuntimeExceptionFactory factory) {
		return input -> {
			return t -> {
				try {
					return input.apply(t);
				} catch (Throwable e) {
					throw factory.build(e);
				}
			};
		};
	}

	/**
	 * to function
	 * @return {@link Convert}
	 * @param <T> left type
	 * @param <R> right type
	 */
	static <T, R> Convert<ExceptionableFunction<T, R>, Function<T, R>> toFunction() {
		return toFunction(RuntimeExceptionFactory.DEFAULT);
	}

	/**
	 * compose
	 * @param before before {@link ExceptionableFunction}
	 * @return new {@link ExceptionableFunction}
	 * @param <V>
	 */
	default <V> ExceptionableFunction<V, R> compose(ExceptionableFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return v -> this.apply(before.apply(v));
	}

	/**
	 *
	 * @param after
	 * @return
	 * @param <V>
	 */
	default <V> ExceptionableFunction<T, V> andThen(ExceptionableFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return t -> after.apply(this.apply(t));
	}
	
	static <T> ExceptionableFunction<T, T> identity() {
		return t -> t;
	}
}
