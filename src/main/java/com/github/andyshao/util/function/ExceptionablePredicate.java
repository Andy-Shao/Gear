package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * 
 * 
 * Title:<br>
 * Description:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @see Predicate
 */
public interface ExceptionablePredicate<T> {
	/**
	 * test operation
	 * @param t test data
	 * @return is passed
	 * @throws Throwable any error
	 */
	boolean test(T t) throws Throwable;

	/**
	 * to predicate
	 * @param f exception factory
	 * @return {@link Predicate}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionablePredicate<T>, Predicate<T>> toPredicate(RuntimeExceptionFactory<?> f) {
		return input -> {
			return t -> {
				try {
					return input.test(t);
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * to predicate
	 * @return {@link Predicate}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionablePredicate<T>, Predicate<T>> toPredicate() {
		return toPredicate(RuntimeExceptionFactory.DEFAULT);
	}

	/**
	 *
	 * @param other
	 * @return
	 */
	default ExceptionablePredicate<T> and(ExceptionablePredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> this.test(t) && other.test(t);
	}

	/**
	 * negate
	 * @return turn over the {@link ExceptionablePredicate} result.
	 */
	default ExceptionablePredicate<T> negate() {
		return t -> !this.test(t);
	}

	/**
	 * negate
	 * @param predicate {@link ExceptionablePredicate}
	 * @return turn over the {@link ExceptionablePredicate}
	 * @param <T> data type
	 */
	static <T> ExceptionablePredicate<T> negate(ExceptionablePredicate<T> predicate) {
		return predicate.negate();
	}

	/**
	 * or operation
	 * @param another another operation
	 * @return any one is passed, then true
	 */
	default ExceptionablePredicate<T> or(ExceptionablePredicate<? super T> another) {
		Objects.requireNonNull(another);
		return t -> this.test(t) || another.test(t);
	}

	/**
	 * is equal operation
	 * @param targetRef target object
	 * @return is equal
	 * @param <T> data type
	 */
	static <T> ExceptionablePredicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull : targetRef::equals;
    }
}
