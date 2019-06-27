package com.github.andyshao.lang;

import java.util.Comparator;
import java.util.Objects;

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
	public static final Comparator<Double> comparator() {
		return  (o1, o2) -> {
			if(Objects.isNull(o1) && Objects.nonNull(o2)) return -1;
			else if(Objects.isNull(o1) && Objects.isNull(o2)) return 0;
			else if(Objects.nonNull(o1) && Objects.isNull(o2)) return 1;
			return Double.compare(o1, o2);
		};
	}
	
	private DoubleOperation() {}
}
