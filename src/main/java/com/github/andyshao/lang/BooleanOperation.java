package com.github.andyshao.lang;

import java.util.Comparator;
import java.util.Objects;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 18, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class BooleanOperation {
	private BooleanOperation() {}
	/**Default boolean {@link Comparator}*/
	public static final Comparator<Boolean> COMPARATOR = Boolean::compare;

	/**
	 * not null and true
	 * @param bol {@link Boolean}
	 * @return it is not null and true then true
	 */
	public static final boolean nonNullAndTrue(Boolean bol) {
		if(Objects.isNull(bol)) return false;
		if(Objects.equals(bol, Boolean.TRUE)) return true;
		return false;
	}

	/**
	 * not null and false
	 * @param bol {@link Boolean}
	 * @return if it is not null and false then true
	 */
	public static final boolean nonNullAndFalse(Boolean bol) {
		if(Objects.isNull(bol)) return false;
		if(Objects.equals(bol, Boolean.FALSE)) return true;
		return false;
	}

	/**
	 * is null or true
	 * @param bol {@link Boolean}
	 * @return if it is null or true then true
	 */
	public static final boolean isNullOrTrue(Boolean bol) {
		if(Objects.isNull(bol)) return true;
		if(Objects.equals(bol, Boolean.TRUE)) return true;
		return false;
	}

	/**
	 * is null or false
	 * @param bol {@link Boolean}
	 * @return if it is null or false then true
	 */
	public static final boolean isNullOrFalse(Boolean bol) {
		if(Objects.isNull(bol)) return true;
		if(Objects.equals(bol, Boolean.FALSE)) return true;
		return false;
	}
}
