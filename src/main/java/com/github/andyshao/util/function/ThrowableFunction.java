package com.github.andyshao.util.function;

import java.util.Objects;
import java.util.function.Function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {
	R apply(T t) throws Throwable;

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
	
	default <V> ThrowableFunction<V, R> compose(ThrowableFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return v -> this.apply(before.apply(v));
	}
	
	default <V> ThrowableFunction<T, V> andThen(ThrowableFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return t -> after.apply(this.apply(t));
	}
	
	static <T> ThrowableFunction<T, T> identity() {
		return t -> t;
	}
}
