package com.github.andyshao.lang;

import java.util.Comparator;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 11, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ComparableOperation {
	private ComparableOperation() {}

	/**
	 * Reverse {@link java.util.Comparator}
	 * @param comparaRet compare result
	 * @return reverse result
	 */
	public static final int reversed(int comparaRet) {
		if(comparaRet < 0) return 1;
		else if(comparaRet > 0) return -1;
		else return 0;
	}

	/**
	 * Reveese {@link Comparator}
	 * @param origin origin {@link Comparator}
	 * @return reverse {@link Comparator}
	 * @param <T> data type
	 */
	public static final <T> Comparator<T> reverse(Comparator<T> origin) {
		return (o1, o2) -> reversed(origin.compare(o1, o2));
	}
}
