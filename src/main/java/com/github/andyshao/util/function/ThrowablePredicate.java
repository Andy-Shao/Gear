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
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @see ExceptionablePredicate
 * @see Predicate
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
@FunctionalInterface
public interface ThrowablePredicate<T> {
	boolean test(T t) throws Throwable;
	
	static <T> Convert<ThrowablePredicate<T>, ExceptionablePredicate<T>> toExceptionablePredicate() {
		return input -> {
			return new ExceptionablePredicate<T>() {
			
				@Override
				public boolean test(T t) throws Exception {
					try {
						return input.test(t);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}
	
	default ThrowablePredicate<T> and(ThrowablePredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> this.test(t) && other.test(t);
	}
	
	default ThrowablePredicate<T> negate() {
		return t -> !this.test(t);
	}
	
	static <T> ThrowablePredicate<T> negate(ThrowablePredicate<T> predicate) {
		return predicate.negate();
	}
	
	default ThrowablePredicate<T> or(ThrowablePredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> this.test(t) || other.test(t);
	}
	
	static <T> ThrowablePredicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }
}
