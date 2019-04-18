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
	public static final Comparator<Boolean> COMPARATOR = Boolean::compare;
	
	public static final boolean nonNullAndTrue(Boolean bol) {
		if(Objects.isNull(bol)) return false;
		if(Objects.equals(bol, Boolean.TRUE)) return true;
		return false;
	}
	
	public static final boolean nonNullAndFalse(Boolean bol) {
		if(Objects.isNull(bol)) return false;
		if(Objects.equals(bol, Boolean.FALSE)) return true;
		return false;
	}
	
	public static final boolean isNullOrTrue(Boolean bol) {
		if(Objects.isNull(bol)) return true;
		if(Objects.equals(bol, Boolean.TRUE)) return true;
		return false;
	}
	
	public static final boolean isNullOrFalse(Boolean bol) {
		if(Objects.isNull(bol)) return true;
		if(Objects.equals(bol, Boolean.FALSE)) return true;
		return false;
	}
}
