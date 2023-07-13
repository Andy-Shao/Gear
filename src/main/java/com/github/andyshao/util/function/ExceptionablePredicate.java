package com.github.andyshao.util.function;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

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
 * @see Predicate
 */
public interface ExceptionablePredicate<T> {
	boolean test(T t) throws Throwable;
	
	static <T> Convert<ExceptionablePredicate<T>, Predicate<T>> toPredicate(RuntimeExceptionFactory f) {
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
	
	static <T> Convert<ExceptionablePredicate<T>, Predicate<T>> toPredicate() {
		return toPredicate(RuntimeExceptionFactory.DEFAULT);
	}
	
	default ExceptionablePredicate<T> and(ExceptionablePredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> this.test(t) && other.test(t);
	}
	
	default ExceptionablePredicate<T> negate() {
		return t -> !this.test(t);
	}
	
	static <T> ExceptionablePredicate<T> negate(ExceptionablePredicate<T> predicate) {
		return predicate.negate();
	}
	
	default ExceptionablePredicate<T> or(ExceptionablePredicate<? super T> other) {
		Objects.requireNonNull(other);
		return t -> this.test(t) || other.test(t);
	}
	
	static <T> ExceptionablePredicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }
}
