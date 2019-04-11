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
public final class DoubleOperation {
	public static final Comparator<Double> COMPARATOR = Double::compare;
	
	private DoubleOperation() {}
}
