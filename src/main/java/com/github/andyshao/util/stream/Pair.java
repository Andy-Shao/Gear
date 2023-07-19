package com.github.andyshao.util.stream;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

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

	/**
	 * build {@link Pair}
	 * @param first first item
	 * @param second second item
	 * @return {@link Pair}
	 * @param <F> first type
	 * @param <S> second type
	 */
	public static <F, S> Pair<F, S> of(F first, S second) {
		return new Pair<F, S>(first, second);
	}

	/**
	 * get first or default
	 * @param def default value
	 * @return first or default value
	 */
	public F getFirstOrDefault(F def) {
		return Objects.isNull(this.first) ? def : this.first;
	}

	/**
	 * compute first if absence
	 * @param supplier default value function
	 * @return first or default value
	 */
	public F computeFirstIfAbsence(Supplier<F> supplier) {
		Optional<F> ops = this.getFirstOps();
		if(ops.isPresent()) return ops.get();
		return supplier.get();
	}

	/**
	 * get second or default
	 * @param def default value
	 * @return sencond or default value
	 */
	public S getSecondOrDefault(S def) {
		return Objects.isNull(this.second) ? def : this.second;
	}

	/**
	 * compute second if absence
	 * @param supplier default value function
	 * @return second value
	 */
	public S computeSecondIfAbsence(Supplier<S> supplier) {
		Optional<S> ops = this.getSecondOps();
		if(ops.isPresent()) return ops.get();
		return supplier.get();
	}

	/**
	 * get first
	 * @return first value
	 */
	public Optional<F> getFirstOps() {
		return Optional.ofNullable(this.first);
	}

	/**
	 * get second
	 * @return second value
	 */
	public Optional<S> getSecondOps() {
		return Optional.ofNullable(this.second);
	}
}
