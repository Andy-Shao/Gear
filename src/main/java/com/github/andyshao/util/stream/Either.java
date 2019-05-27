package com.github.andyshao.util.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import com.github.andyshao.util.function.ExceptionableFunction;

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
 * @param <L> left type
 * @param <R> right type
 */
@RequiredArgsConstructor
public final class Either<L, R> {
	private final L left;
	private final R right;
	
	public static <L, R> Either<L, R> left(L value) {
		return new Either<L, R>(value, null);
	}
	
	public static <L, R> Either<L, R> right(R value) {
		return new Either<L, R>(null, value);
	}
	
	public Optional<L> getLeft() {
		return Optional.ofNullable(this.left);
	}
	
	public Optional<R> getRight() {
		return Optional.ofNullable(this.right);
	}
	
	public boolean isLeft() {
		return Objects.nonNull(this.left);
	}
	
	public boolean isRight() {
		return Objects.nonNull(this.right);
	}
	
	public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
		if(this.isLeft()) return Optional.of(mapper.apply(this.left));
		return Optional.empty();
	}
	
	public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
		if(this.isRight()) return Optional.of(mapper.apply(this.right));
		return Optional.empty();
	}
	
	public static <T, R> Function<T, Either<Exception, R>> lift(ExceptionableFunction<T, R> function) {
		return t -> {
			try {
				return Either.right(function.apply(t));
			} catch (Exception e) {
				return Either.left(e);
			}
		};
	}
	
	public static <T, R> Function<T, Either<Pair<Exception, T>, R>> liftWithValue(ExceptionableFunction<T, R> function) {
		return t -> {
			try {
				return Either.right(function.apply(t));
			} catch (Exception e) {
				return Either.left(Pair.of(e, t));
			}
		};
	}

	@Override
	public String toString() {
		if(this.isLeft()) return String.format("Left(%s)", this.left);
		return String.format("Right(%s)", this.right);
	}
}
