package com.github.andyshao.lang;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jul 19, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <IN> input type one
 * @param <IN2> input type two
 * @param <OUT> output type
 */
public interface BiConvert<IN,IN2, OUT> {
	/**
	 * binary covert
	 * @param in one
	 * @param in2 two
	 * @return output object
	 */
	OUT convert(IN in, IN2 in2);
}
