package com.github.andyshao.util.function;

import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 6, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
@FunctionalInterface
public interface CharConsumer {
	/**
	 * accept char
	 * @param value char value
	 */
	void accept(char value);

	/**
	 * and then
	 * @param after after {@link CharConsumer}
	 * @return new {@link CharConsumer}
	 */
	default CharConsumer andThen(CharConsumer after) {
		Objects.requireNonNull(after);
		return it -> {
			this.accept(it);
			after.accept(it);
		};
	}
}
