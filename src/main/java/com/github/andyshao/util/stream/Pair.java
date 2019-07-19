package com.github.andyshao.util.stream;

import java.util.Objects;
import java.util.Optional;

import lombok.AccessLevel;
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
 * @param <F> first object type
 * @param <S> second object type
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Pair<F, S> {
	private final F first;
	private final S second;
	
	public static <F, S> Pair<F, S> of(F first, S second) {
		return new Pair<F, S>(first, second);
	}
	
	public F getFirstWithDefault(F def) {
		return Objects.isNull(this.first) ? def : this.first;
	}
	
	public S getSecondWithDefault(S def) {
		return Objects.isNull(this.second) ? def : this.second;
	}
	
	public Optional<F> getFirstOps() {
		return Optional.ofNullable(this.first);
	}
	
	public Optional<S> getSecondOps() {
		return Optional.ofNullable(this.second);
	}
}
