package com.github.andyshao.util.stream;

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
 */
@FunctionalInterface
public interface RuntimeExceptionFactory {
	static RuntimeExceptionFactory DEFAULT = e -> new RuntimeException(e);
	
	RuntimeException build(Throwable e);
}
