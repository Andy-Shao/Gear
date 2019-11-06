package com.github.andyshao.util.function;

import java.util.Objects;
import java.util.function.BiFunction;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 */
public interface ThrowableBiFunction<T, U, R> {
	R apply(T t, U u) throws Throwable;
	
	static <T, U, R> Convert<ThrowableBiFunction<T, U, R>, ExceptionableBiFunction<T, U, R>> toExceptionableBiFunction() {
		return input -> {
			return new ExceptionableBiFunction<T, U, R>() {
			
				@Override
				public R apply(T t, U u) throws Exception {
					try {
						return input.apply(t, u);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}
	
	default <V> ThrowableBiFunction<T, U, V> andThen(ThrowableFunction<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }
}
