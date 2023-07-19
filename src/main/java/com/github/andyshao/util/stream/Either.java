package com.github.andyshao.util.stream;

import com.github.andyshao.util.function.ExceptionableFunction;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

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
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Either<L, R> {
	private final L left;
	private final R right;

	/**
	 * set left value
	 * @param value left value
	 * @return {@link Either}
	 * @param <L> left type
	 * @param <R> right type
	 */
	public static <L, R> Either<L, R> left(L value) {
		return new Either<L, R>(value, null);
	}

	/**
	 * set right value
	 * @param value right value
	 * @return {@link Either}
	 * @param <L> left type
	 * @param <R> right type
	 */
	public static <L, R> Either<L, R> right(R value) {
		return new Either<L, R>(null, value);
	}

	/**
	 * get left value
	 * @return left value
	 */
	public Optional<L> getLeft() {
		return Optional.ofNullable(this.left);
	}

	/**
	 * compute left if absence
	 * @param function default value function
	 * @return {@link Either#getLeft()} or default value
	 */
	public Optional<L> computeLeftIfAbsence(Function<Either<L, R>, L> function) {
		Optional<L> ret = this.getLeft();
		if(ret.isPresent()) return ret;
		return Optional.ofNullable(function.apply(this));
	}

	/**
	 * get right value
	 * @return right value
	 */
	public Optional<R> getRight() {
		return Optional.ofNullable(this.right);
	}

	/**
	 * compute right if absence
	 * @param function default value function
	 * @return {@link Either#getRight()} or default value
	 */
	public Optional<R> computeRightIfAbsence(Function<Either<L, R>, R> function) {
		Optional<R> ret = this.getRight();
		if(ret.isPresent()) return ret;
		return Optional.ofNullable(function.apply(this));
	}

	/**
	 * the left side has the value
	 * @return if it has then true
	 */
	public boolean isLeft() {
		return Objects.nonNull(this.left);
	}

	/**
	 * the right side has the value
	 * @return if it has then true
	 */
	public boolean isRight() {
		return Objects.nonNull(this.right);
	}

	/**
	 * map left
	 * @param mapper mapper function
	 * @return data
	 * @param <T> data type
	 */
	public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
		if(this.isLeft()) return Optional.of(mapper.apply(this.left));
		return Optional.empty();
	}

	/**
	 * map left
	 * @param func {@link Function}
	 * @return {@link Either}
	 * @param <T> data type
	 */
	public <T> Either<T, R> mapLft(Function<? super L, T> func) {
		if(isLeft()) return Either.left(func.apply(this.left));
		else return Either.right(this.right);
	}

	/**
	 * flat map left
	 * @param func {@link Function}
	 * @return {@link Either}
	 * @param <T> data type
	 */
	public <T> Either<T, R> flatMapLft(Function<? super L, Either<T, R>> func) {
		if(isLeft()) return func.apply(this.left);
		else return Either.right(this.right);
	}

	/**
	 * map right
	 * @param mapper {@link Function}
	 * @return {@link Either}
	 * @param <T> data type
	 */
	public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
		if(this.isRight()) return Optional.of(mapper.apply(this.right));
		return Optional.empty();
	}

	/**
	 * map right
	 * @param func {@link Function}
	 * @return {@link Either}
	 * @param <T> data type
	 */
	public <T> Either<L, T> mapRht(Function<? super R, T> func) {
		if(isRight()) return Either.right(func.apply(this.right));
		else return Either.left(this.left);
	}

	/**
	 * flat map right
	 * @param func {@link Function}
	 * @return {@link Either}
	 * @param <T> data type
	 */
	public <T> Either<L, T> flatMapRht(Function<? super R, Either<L, T>> func){
		if(isRight()) return func.apply(this.right);
		else return Either.left(this.left);
	}

	/**
	 * fun expression
	 * @param function {@link ExceptionableFunction}
	 * @return {@link Function}
	 * @param <T> input type
	 * @param <R> right type
	 */
	public static <T, R> Function<T, Either<Throwable, R>> funExp(ExceptionableFunction<T, R> function) {
		return t -> {
			try {
				return Either.right(function.apply(t));
			} catch (Throwable e) {
				return Either.left(e);
			}
		};
	}

	/**
	 * fun expression with value
	 * @param function {@link ExceptionableFunction}
	 * @return {@link Function}
	 * @param <T> input type
	 * @param <R> right type
	 */
	public static <T, R> Function<T, Either<Pair<Throwable, T>, R>> funExpWithValue(ExceptionableFunction<T, R> function) {
		return t -> {
			try {
				return Either.right(function.apply(t));
			} catch (Throwable e) {
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
