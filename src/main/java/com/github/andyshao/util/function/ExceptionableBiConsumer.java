package com.github.andyshao.util.function;

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
 */
public interface ExceptionableBiConsumer<T, U> {
	void accept(T t, U u) throws Exception;
}