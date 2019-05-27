package com.github.andyshao.util.function;

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
 */
@FunctionalInterface
public interface ExceptionableConsumer<T> {
	void accept(T t) throws Exception;
}
