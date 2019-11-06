package com.github.andyshao.util.function;

import java.util.Objects;
import java.util.function.BiConsumer;

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
 * @see BiConsumer
 */
public interface ExceptionableBiConsumer<T, U> {
	void accept(T t, U u) throws Exception;
	
	static <T,U> Convert<ExceptionableBiConsumer<T, U>, BiConsumer<T, U>> toBiConsumer(RuntimeExceptionFactory factory) {
		return input -> {
			return (t, u) -> {
				try {
					input.accept(t, u);
				} catch (Exception e) {
					throw factory.build(e);
				}
			};
		};
	}
	
	static <T, U> Convert<ExceptionableBiConsumer<T, U>, BiConsumer<T, U>> toBiConsumer() {
		return toBiConsumer(RuntimeExceptionFactory.DEFAULT);
	}
	
	default ExceptionableBiConsumer<T, U> andThen(ExceptionableBiConsumer<? super T, ? super U> after) {
		Objects.requireNonNull(after);
		return (t, u) -> {
			this.accept(t, u);
			after.accept(t, u);
		};
	}
}
