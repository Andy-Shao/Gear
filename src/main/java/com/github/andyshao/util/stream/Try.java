package com.github.andyshao.util.stream;

import com.github.andyshao.util.ExceptionableComparator;
import com.github.andyshao.util.function.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;

/**
 * 
 * 
 * Title:<br>
 * Description:<br>
 * Copyright: Copryright(c) May 27, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> argument type
 * @param <R> resource type
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Try<T, R> {
	private final Throwable failure;
	private final T argument;
	private final R success;
	private final boolean isSuccess;

	/**
	 * success operation
	 * @param argument parameter
	 * @param resource return value
	 * @return a success {@link Try}
	 * @param <T> argument type
	 * @param <R> resource type
	 */
	public static <T, R> Try<T, R> success(T argument, R resource) {
		return new Try<>(null, argument, resource, true);
	}

	/**
	 * failure operation
	 * @param argument parameter
	 * @param e error
	 * @return a failure {@link Try}
	 * @param <T> argument type
	 * @param <R> resource type
	 */
	public static <T, R> Try<T, R> failure(T argument, Throwable e) {
		return new Try<>(e, argument, null, false);
	}

	/**
	 * is success
	 * @return if it is then true
	 */
	public boolean isSuccess() {
		return this.isSuccess;
	}

	/**
	 * is failure
	 * @return if it is then true
	 */
	public boolean isFailure() {
		return !isSuccess();
	}

	/**
	 * get argument
	 * @return return type
	 */
	public Optional<T> getArgumentOps() {
		return Optional.ofNullable(this.argument);
	}

	/**
	 * compute if argument absence
	 * @param supplier {@link Supplier}
	 * @return argument
	 */
	public T computeIfArgumentAbsence(Supplier<T> supplier) {
		Optional<T> ops = getArgumentOps();
		return ops.isPresent() ? ops.get() : supplier.get();
	}

	/**
	 * get success result
	 * @return success result
	 */
	public Optional<R> getSuccessOps() {
		return Optional.ofNullable(this.success);
	}

	/**
	 * compute if success absence
	 * @param supplier {@link Supplier}
	 * @return success result or default
	 */
	public R computeIfSuccessAbsence(Supplier<R> supplier) {
		Optional<R> successOps = this.getSuccessOps();
		return successOps.isPresent() ? successOps.get() : supplier.get();
	}

	/**
	 * get failure
	 * @return the error
	 */
	public Optional<Throwable> getFailureOps() {
		return Optional.ofNullable(this.failure);
	}

	/**
	 * compute if failure absence
	 * @param supplier {@link Supplier}
	 * @return the error
	 */
	public Throwable computeIfFailureAbsence(Supplier<Throwable> supplier) {
		Optional<Throwable> failureOps = this.getFailureOps();
		return failureOps.isPresent() ? failureOps.get() : supplier.get();
	}

	/**
	 * double function expression
	 * @param fun {@link ExceptionableToDoubleFunction}
	 * @param errorProcess error process
	 * @return {@link ToDoubleFunction}
	 * @param <T> data type
	 */
	public static <T> ToDoubleFunction<T> doubleFunExp(ExceptionableToDoubleFunction<T> fun, 
			Function<Try<T, Void>, Double> errorProcess) {
		return t -> {
			try {
				return fun.applyAsDouble(t);
			} catch (Throwable e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}

	/**
	 * long function expression
	 * @param fun {@link ExceptionableToLongFunction}
	 * @param errorProcess error process
	 * @return {@link ToLongFunction}
	 * @param <T> data type
	 */
	public static <T> ToLongFunction<T> longFunExp(ExceptionableToLongFunction<T> fun, 
			Function<Try<T, Void>, Long> errorProcess) {
		return t -> {
			try {
				return fun.applyAsLong(t);
			} catch (Throwable e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}

	/**
	 * int function expression
	 * @param fun function
	 * @param errorProcess error process
	 * @return {@link ToIntFunction}
	 * @param <T> data type
	 */
	public static <T> ToIntFunction<T> intFunExp(ExceptionableToIntFunction<T> fun, 
			Function<Try<T, Void>, Integer> errorProcess) {
		return t -> {
			try {
				return fun.applyAsInt(t);
			} catch (Throwable e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}

	/**
	 * compute expression
	 * @param comp compute
	 * @param errorProcess error process
	 * @return {@link Comparator}
	 * @param <T> data type
	 */
	public static <T> Comparator<T> compExp(ExceptionableComparator<T> comp, 
			Function<Try<Pair<T, T>, Void>, Integer> errorProcess) {
		return (o1, o2) -> {
			try {
				return comp.compare(o1, o2);
			} catch (Throwable e) {
				return errorProcess.apply(Try.failure(Pair.of(o1, o2), e));
			}
		};
	}

	/**
	 * sup expression
	 * @param supplier {@link Supplier}
	 * @return {@link Try}
	 * @param <R> data type
	 */
	public static <R> Supplier<Try<Void, R>> supExp(ExceptionableSupplier<R> supplier) {
		return () -> {
			try {
				return Try.success(null, supplier.get());
			} catch (Throwable e) {
				return Try.failure(null, e);
			}
		};
	}

	/**
	 * function expression
	 * @param function {@link ExceptionableFunction}
	 * @return {@link Try}
	 * @param <T> input type
	 * @param <R> output type
	 */
	public static <T, R> Function<T, Try<T, R>> funExp(ExceptionableFunction<T, R> function) {
		return t -> {
			try {
				return Try.success(t, function.apply(t));
			} catch (Throwable e) {
				return Try.failure(t, e);
			}
		};
	}

	/**
	 * binary function expression
	 * @param function {@link ExceptionableBiFunction}
	 * @return {@link Try}
	 * @param <T> left argument
	 * @param <U> right argument
	 * @param <R> output type
	 */
	public static <T, U, R> BiFunction<T, U, Try<Pair<T, U>, R>> biFunExp(ExceptionableBiFunction<T, U, R> function) {
		return (t,u) -> {
			try {
				return Try.success(Pair.of(t, u), function.apply(t, u));
			} catch (Throwable e) {
				return Try.failure(Pair.of(t, u), e);
			}
		};
	}

	/**
	 * consumer expression
	 * @param consumer {@link ExceptionableConsumer}
	 * @param errorProcess error process
	 * @return {@link Try}
	 * @param <T> data type
	 */
	public static <T> Consumer<T> consumExp(ExceptionableConsumer<T> consumer, Consumer<Try<T, Void>> errorProcess) {
		return it -> {
			try {
				consumer.accept((T) it);
			} catch (Throwable e) {
				errorProcess.accept(Try.failure(it, e));
			}
		};
	}

	/**
	 * binary consumer expression
	 * @param consumer {@link ExceptionableConsumer}
	 * @param errorProcess error process
	 * @return {@link BiConsumer}
	 * @param <T> input type
	 * @param <U> output type
	 */
	public static <T,U> BiConsumer<T, U> biConsumExp(ExceptionableBiConsumer<T, U> consumer, 
			Consumer<Try<Pair<T, U>,Void>> errorProcess) {
		return (t, u) -> {
			try {
				consumer.accept(t, u);
			} catch (Throwable e) {
				errorProcess.accept(Try.failure(Pair.of(t, u), e));
			}
		};
	}

	/**
	 * predicate expression
	 * @param predicate {@link ExceptionablePredicate}
	 * @param errorProcess error process
	 * @return {@link Predicate}
	 * @param <T> data type
	 */
	public static <T> Predicate<T> predExp(ExceptionablePredicate<T> predicate, 
			Function<Try<T, Void>, Boolean> errorProcess) {
		return t -> {
			try {
				return predicate.test(t);
			} catch (Throwable e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}

	/**
	 * binary predicate expression
	 * @param predicate {@link ExceptionablePredicate}
	 * @param errorProcess error process
	 * @return {@link BiPredicate}
	 * @param <T> input type
	 * @param <U> output type
	 */
	public static <T, U> BiPredicate<T, U> biPredExp(ExceptionableBiPredicate<T, U> predicate, 
			Function<Try<Pair<T, U>, Void>, Boolean> errorProcess) {
		return (t, u) -> {
			try {
				return predicate.test(t, u);
			} catch (Throwable e) {
				return errorProcess.apply(Try.failure(Pair.of(t, u), e));
			}
		};
	}
}
