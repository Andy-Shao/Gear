package com.github.andyshao.util;

import com.github.andyshao.lang.Convert;
import com.github.andyshao.util.stream.ThrowableException;

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
 * @see ExceptionableComparator
 * @see Comparator
 * @deprecated repeated
 */
@Deprecated(since = "5.0.0.RELEASE")
public interface ThrowableComparator<T> {
	/**
	 * compare
	 * @param o1 one
	 * @param o2 two
	 * @return compare result
	 * @throws Throwable any error
	 */
	int compare(T o1, T o2) throws Throwable;

	/**
	 * to {@link ExceptionableComparator}
	 * @return {@link ExceptionableComparator}
	 * @param <T> data type
	 */
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
