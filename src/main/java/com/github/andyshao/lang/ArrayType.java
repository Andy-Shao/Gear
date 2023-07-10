package com.github.andyshao.lang;

import lombok.Getter;

import java.util.Optional;

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

	/**
	 * match the {@link ArrayType} from {@link Class}
	 * @param clazz {@link Class}
	 * @return {@link ArrayType}
	 */
	public static final Optional<ArrayType> match(Class<?> clazz) {
		if(clazz.isArray()) {
			if(clazz.isAssignableFrom(char[].class)) return Optional.of(CHAR_ARRAY);
			else if(clazz.isAssignableFrom(byte[].class)) return Optional.of(BYTE_ARRAY);
			else if(clazz.isAssignableFrom(short[].class)) return Optional.of(SHORT_ARRAY);
			else if(clazz.isAssignableFrom(int[].class)) return Optional.of(INT_ARRAY);
			else if(clazz.isAssignableFrom(long[].class)) return Optional.of(LONG_ARRAY);
			else if(clazz.isAssignableFrom(double[].class)) return Optional.of(DOUBLE_ARRAY);
			else if(clazz.isAssignableFrom(float[].class)) return Optional.of(FLOAT_ARRAY);
			else return Optional.of(OBJ_ARRAY);
		}
		return Optional.empty();
	}
}
