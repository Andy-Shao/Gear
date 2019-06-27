package com.github.andyshao.lang;

import lombok.Getter;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public enum ArrayType {
	INT_ARRAY(int[].class), BYTE_ARRAY(byte[].class), CHAR_ARRAY(char[].class), SHORT_ARRAY(short[].class), 
	FLOAT_ARRAY(float[].class), DOUBLE_ARRAY(double[].class), LONG_ARRAY(long[].class), OBJ_ARRAY(null);
	@Getter
	private final Class<?> type;
	private ArrayType(Class<?> type) {
		this.type = type;
	}
}
