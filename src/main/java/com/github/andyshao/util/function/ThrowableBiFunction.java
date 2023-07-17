package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.Objects;
import java.util.function.BiFunction;

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
 * @param <U> argument type
 * @param <R> return type
 * @see ExceptionableBiFunction
 * @see BiFunction
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableBiFunction<T, U, R> {
	/**
	 * apply operation
	 * @param t left type
	 * @param u right type
	 * @return ret
	 * @throws Throwable any error
	 */
	R apply(T t, U u) throws Throwable;

	/**
	 * to {@link ExceptionableBiFunction}
	 * @return {@link ExceptionableBiFunction}
	 * @param <T> left type
	 * @param <U> right type
	 * @param <R> return type
	 */
	static <T, U, R> Convert<ThrowableBiFunction<T, U, R>, ExceptionableBiFunction<T, U, R>> toExceptionableBiFunction() {
		return input -> {
			return new ExceptionableBiFunction<T, U, R>() {
			
				@Override
				public R apply(T t, U u) throws Throwable {
					try {
						return input.apply(t, u);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}

	/**
	 * and then
	 * @param after after {@link ThrowableFunction}
	 * @return {@link ThrowableFunction}
	 * @param <V> data type
	 */
	default <V> ThrowableBiFunction<T, U, V> andThen(ThrowableFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
