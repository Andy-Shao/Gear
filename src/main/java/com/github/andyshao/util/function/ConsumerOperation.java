package com.github.andyshao.util.function;

import java.util.function.Consumer;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Nov 1, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ConsumerOperation {
	private ConsumerOperation() {}

	public static final <T> Consumer<T> lambda(Consumer<T> consumer) {
		return consumer;
	}
}
