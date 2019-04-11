package com.github.andyshao.lang;

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
	
	public static final int reversed(int comparaRet) {
		if(comparaRet < 0) return 1;
		else if(comparaRet > 0) return -1;
		else return 0;
	}
}
