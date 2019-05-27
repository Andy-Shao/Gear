package com.github.andyshao.util.stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <F> first
 * @param <S> second
 */
@Getter
@RequiredArgsConstructor
public class Pair<F, S> {
	private final F first;
	private final S second;
	
	public static <F, S> Pair<F, S> of(F first, S second) {
		return new Pair<F, S>(first, second);
	}
}
