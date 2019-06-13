package com.github.andyshao.util;

import java.util.Comparator;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 13, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> the type of objects that may be compared by this comparator
 * @see ExceptionableComparator
 * @see Comparator
 */
public interface ThrowableComparator<T> {
	int compare(T o1, T o2) throws Throwable;
	
	static <T> Convert<ThrowableComparator<T>, ExceptionableComparator<T>> toExceptionableComparator() {
		return input -> {
			return (o1, o2) -> {
				try {
					return input.compare(o1, o2);
				} catch (Throwable e) {
					throw ThrowableException.convertToException(e);
				}
			};
		};
	}
}
