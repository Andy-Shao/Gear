package com.github.andyshao.util;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.RuntimeExceptionFactory;

import java.util.Comparator;

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
 * @see Comparator
 */
@FunctionalInterface
public interface ExceptionableComparator<T> {
	/**
	 * compare
	 * @param o1 left item
	 * @param o2 right item
	 * @return compare result
	 * @throws Throwable any error
	 */
	int compare(T o1, T o2) throws Throwable;

	/**
	 * to comparator
	 * @param f exception factory
	 * @return return type
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableComparator<T>, Comparator<T>> toComparator(RuntimeExceptionFactory<?> f) {
		return input -> {
			return (o1, o2) -> {
				try {
					return input.compare(o1, o2);
				} catch (Throwable e) {
					throw f.build(e);
				}
			};
		};
	}

	/**
	 * from {@link ExceptionableComparator} to {@link Comparator}
	 * @return {@link Comparator}
	 * @param <T> data type
	 */
	static <T> Convert<ExceptionableComparator<T>, Comparator<T>> toComparator() {
		return toComparator(RuntimeExceptionFactory.DEFAULT);
	}
}
