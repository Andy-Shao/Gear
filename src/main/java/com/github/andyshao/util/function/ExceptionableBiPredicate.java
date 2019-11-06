package com.github.andyshao.util.function;

import java.util.Objects;
import java.util.function.BiPredicate;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

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
public interface ExceptionableBiPredicate<T, U> {
	boolean test(T t, U u) throws Exception;
	
	static <T, U> Convert<ExceptionableBiPredicate<T, U>, BiPredicate<T, U>> toBiPredicate(RuntimeExceptionFactory f) {
		return input -> {
			return (t, u) -> {
				try {
					return input.test(t, u);
				} catch (Exception e) {
					throw f.build(e);
				}
			};
		};
	}
	
	static <T, U> Convert<ExceptionableBiPredicate<T, U>, BiPredicate<T, U>> toBiPredicate() {
		return toBiPredicate(RuntimeExceptionFactory.DEFAULT);
	}
	
	default ExceptionableBiPredicate<T, U> and(ExceptionableBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) && other.test(t, u);
    }
	
	default ExceptionableBiPredicate<T, U> negate() {
        return (T t, U u) -> !test(t, u);
    }
	
	static <T, U> ExceptionableBiPredicate<T, U> negate(ExceptionableBiPredicate<T, U> predicate) {
		return predicate.negate();
	}
	
	default ExceptionableBiPredicate<T, U> or(ExceptionableBiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (T t, U u) -> test(t, u) || other.test(t, u);
    }
}
