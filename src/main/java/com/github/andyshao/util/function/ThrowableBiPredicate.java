package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

import java.util.Objects;
import java.util.function.Predicate;

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
 * @param <U> argujent type
 * @see ExceptionablePredicate
 * @see Predicate
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableBiPredicate<T, U> {
	/**
	 * test
	 * @param t left type
	 * @param u right type
	 * @return test result
	 * @throws Throwable any error
	 */
	boolean test(T t, U u) throws Throwable;

	/**
	 * to {@link ExceptionableBiPredicate}
	 * @return {@link ExceptionableBiPredicate}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> Convert<ThrowableBiPredicate<T, U>, ExceptionableBiPredicate<T, U>> toExceptionableBiPredicate() {
		return input -> {
			return new ExceptionableBiPredicate<T, U>() {
			
				@Override
				public boolean test(T t, U u) throws Exception {
					try {
						return input.test(t, u);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}

	/**
	 * and operation
	 * @param another another {@link ThrowableBiPredicate}
	 * @return a new {@link ThrowableBiPredicate}
	 */
	default ThrowableBiPredicate<T, U> and(ThrowableBiPredicate<? super T, ? super U> another) {
        Objects.requireNonNull(another);
        return (T t, U u) -> test(t, u) && another.test(t, u);
    }

	/**
	 * negate
	 * @return turn over the result
	 */
	default ThrowableBiPredicate<T, U> negate() {
        return (T t, U u) -> !test(t, u);
    }

	/**
	 * negate operation
	 * @param predicate {@link ThrowableBiPredicate}
	 * @return a new {@link ThrowableBiPredicate}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> ThrowableBiPredicate<T, U> negate(ThrowableBiPredicate<T, U> predicate) {
		return predicate.negate();
	}

	/**
	 * or operation
	 * @param another another {@link ThrowableBiPredicate}
	 * @return a new {@link ThrowableBiPredicate}
	 */
	default ThrowableBiPredicate<T, U> or(ThrowableBiPredicate<? super T, ? super U> another) {
        Objects.requireNonNull(another);
        return (T t, U u) -> test(t, u) || another.test(t, u);
    }
}
