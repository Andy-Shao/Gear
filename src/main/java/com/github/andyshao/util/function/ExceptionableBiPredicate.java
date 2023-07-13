package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.Objects;
import java.util.function.BiPredicate;

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
 * @see BiPredicate
 */
@FunctionalInterface
public interface ExceptionableBiPredicate<T, U> {
	/**
	 * test
	 * @param t left
	 * @param u right
	 * @return is pass the test
	 * @throws Throwable any error
	 */
	boolean test(T t, U u) throws Throwable;

	/**
	 * to {@link BiPredicate}
	 * @param f exception convert factory
	 * @return {@link Convert}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> Convert<ExceptionableBiPredicate<T, U>, BiPredicate<T, U>> toBiPredicate(RuntimeExceptionFactory f) {
		return input -> {
			return (t, u) -> {
				try {
					return input.test(t, u);
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to {@link BiPredicate}
	 * @return {@link Convert}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> Convert<ExceptionableBiPredicate<T, U>, BiPredicate<T, U>> toBiPredicate() {
		return toBiPredicate(RuntimeExceptionFactory.DEFAULT);
	}

	/**
	 * and operation
	 * @param other another {@link ExceptionableBiPredicate}
	 * @return new {@link ExceptionableBiPredicate}
	 */
	default ExceptionableBiPredicate<T, U> and(ExceptionableBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) && other.test(t, u);
    }

	/**
	 * negate operation
	 * @return {@link ExceptionableBiPredicate}
	 */
	default ExceptionableBiPredicate<T, U> negate() {
        return (T t, U u) -> !test(t, u);
    }

	/**
	 * negate operation
	 * @param predicate predicate operation
	 * @return {@link ExceptionableBiPredicate}
	 * @param <T> left type
	 * @param <U> right type
	 */
	static <T, U> ExceptionableBiPredicate<T, U> negate(ExceptionableBiPredicate<T, U> predicate) {
		return predicate.negate();
	}

	/**
	 * or operation
	 * @param other another {@link ExceptionableBiPredicate}
	 * @return new {@link ExceptionableBiPredicate}
	 */
	default ExceptionableBiPredicate<T, U> or(ExceptionableBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) || other.test(t, u);
    }
}
