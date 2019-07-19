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
	OUT convert(IN in, IN2 in2);
}
