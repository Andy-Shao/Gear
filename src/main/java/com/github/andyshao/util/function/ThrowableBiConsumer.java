package com.github.andyshao.util.function;

import java.util.Objects;
import java.util.function.BiConsumer;

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
 * @param <U> artument type
 * @see ExceptionableBiConsumer
 * @see BiConsumer
 */
public interface ThrowableBiConsumer<T, U> {
	void accept(T t, U u) throws Throwable;
	
	static <T, U> Convert<ThrowableBiConsumer<T, U>, ExceptionableBiConsumer<T, U>> toExceptionableBiConsumer() {
		return input -> {
			return new ExceptionableBiConsumer<T, U>() {
				@Override
				public void accept(T t, U u) throws Exception {
					try {
						input.accept(t, u);
					} catch (Throwable e) {
						throw ThrowableException.convertToException(e);
					}
				}
			};
		};
	}
	
	default ThrowableBiConsumer<T, U> andThen(ThrowableBiConsumer<? super T, ? super U> after) {
		Objects.requireNonNull(after);
		return (t, u) -> {
			this.accept(t, u);
			after.accept(t, u);
		};
	}
}
