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
	/**int array type*/
	INT_ARRAY(int[].class),
	/**byte array type*/
	BYTE_ARRAY(byte[].class),
	/**char array type*/
	CHAR_ARRAY(char[].class),
	/**short array type*/
	SHORT_ARRAY(short[].class),
	/**float array type*/
	FLOAT_ARRAY(float[].class),
	/**double array type*/
	DOUBLE_ARRAY(double[].class),
	/**long array type*/
	LONG_ARRAY(long[].class),
	/**object array type*/
	OBJ_ARRAY(null);
	@Getter
	private final Class<?> type;
	private ArrayType(Class<?> type) {
		this.type = type;
	}
}
