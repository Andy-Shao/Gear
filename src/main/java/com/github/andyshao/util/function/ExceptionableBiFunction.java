package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.Objects;
import java.util.function.BiFunction;

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
 * @param <R> return type
 * @see BiFunction
 */
@FunctionalInterface
public interface ExceptionableBiFunction<T, U, R> {
	/**
	 * apply
	 * @param t left
	 * @param u right
	 * @return ret
	 * @throws Exception any error
	 */
	R apply(T t, U u) throws Exception;

	/**
	 * to {@link BiFunction}
	 * @param f error convert factory
	 * @return {@link BiFunction}
	 * @param <T> left type
	 * @param <U> right type
	 * @param <R> return type
	 */
	static <T, U, R> Convert<ExceptionableBiFunction<T, U, R>, BiFunction<T, U, R>> toBiFunction(RuntimeExceptionFactory f) {
		return input -> {
			return (t, u) -> {
				try {
					return input.apply(t, u);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to {@link BiFunction}
	 * @return {@link BiFunction}
	 * @param <T> left type
	 * @param <U> right type
	 * @param <R> return type
	 */
	static <T, U, R> Convert<ExceptionableBiFunction<T, U, R>, BiFunction<T, U, R>> toBiFunction() {
		return toBiFunction(RuntimeExceptionFactory.DEFAULT);
	}

	/**
	 * and then
	 * @param after after
	 * @return new {@link ExceptionableBiFunction}
	 * @param <V> return type
	 */
	default <V> ExceptionableBiFunction<T, U, V> andThen(ExceptionableFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
