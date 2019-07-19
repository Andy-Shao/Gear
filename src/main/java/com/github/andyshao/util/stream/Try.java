package com.github.andyshao.util.stream;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import com.github.andyshao.util.ExceptionableComparator;
import com.github.andyshao.util.function.ExceptionableBiConsumer;
import com.github.andyshao.util.function.ExceptionableBiFunction;
import com.github.andyshao.util.function.ExceptionableBiPredicate;
import com.github.andyshao.util.function.ExceptionableConsumer;
import com.github.andyshao.util.function.ExceptionableFunction;
import com.github.andyshao.util.function.ExceptionablePredicate;
import com.github.andyshao.util.function.ExceptionableSupplier;
import com.github.andyshao.util.function.ExceptionableToDoubleFunction;
import com.github.andyshao.util.function.ExceptionableToIntFunction;
import com.github.andyshao.util.function.ExceptionableToLongFunction;

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
	
	public static <T, R> Try<T, R> success(T argument, R resource) {
		return new Try<>(null, argument, resource, true);
	}
	
	public static <T, R> Try<T, R> failure(T argument, Exception e) {
		return new Try<>(e, argument, null, false);
	}
	
	public boolean isSuccess() {
		return this.isSuccess;
	}
	
	public boolean isFailure() {
		return !isSuccess();
	}
	
	public Optional<T> getArgumentOps() {
		return Optional.ofNullable(this.argument);
	}
	
	public Optional<R> getSuccessOps() {
		return Optional.ofNullable(this.success);
	}
	
	public Optional<Throwable> getFailure() {
		return Optional.ofNullable(this.failure);
	}
	
	public static <T> ToDoubleFunction<T> doubleFunExp(ExceptionableToDoubleFunction<T> fun, 
			Function<Try<T, Void>, Double> errorProcess) {
		return t -> {
			try {
				return fun.applyAsDouble(t);
			} catch (Exception e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}
	
	public static <T> ToLongFunction<T> longFunExp(ExceptionableToLongFunction<T> fun, 
			Function<Try<T, Void>, Long> errorProcess) {
		return t -> {
			try {
				return fun.applyAsLong(t);
			} catch (Exception e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}
	
	public static <T> ToIntFunction<T> intFunExp(ExceptionableToIntFunction<T> fun, 
			Function<Try<T, Void>, Integer> errorProcess) {
		return t -> {
			try {
				return fun.applyAsInt(t);
			} catch (Exception e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}
	
	public static <T> Comparator<T> compExp(ExceptionableComparator<T> comp, 
			Function<Try<Pair<T, T>, Void>, Integer> errorProcess) {
		return (o1, o2) -> {
			try {
				return comp.compare(o1, o2);
			} catch (Exception e) {
				return errorProcess.apply(Try.failure(Pair.of(o1, o2), e));
			}
		};
	}
	
	public static <R> Supplier<Try<Void, R>> supExp(ExceptionableSupplier<R> supplier) {
		return () -> {
			try {
				return Try.success(null, supplier.get());
			} catch (Exception e) {
				return Try.failure(null, e);
			}
		};
	}
	
	public static <T, R> Function<T, Try<T, R>> funExp(ExceptionableFunction<T, R> function) {
		return t -> {
			try {
				return Try.success(t, function.apply(t));
			} catch (Exception e) {
				return Try.failure(t, e);
			}
		};
	}
	
	public static <T, U, R> BiFunction<T, U, Try<Pair<T, U>, R>> biFunExp(ExceptionableBiFunction<T, U, R> function) {
		return (t,u) -> {
			try {
				return Try.success(Pair.of(t, u), function.apply(t, u));
			} catch (Exception e) {
				return Try.failure(Pair.of(t, u), e);
			}
		};
	}
	
	public static <T> Consumer<T> consumExp(ExceptionableConsumer<T> consumer, Consumer<Try<T, Void>> errorProcess) {
		return it -> {
			try {
				consumer.accept((T) it);
			} catch (Exception e) {
				errorProcess.accept(Try.failure(it, e));
			}
		};
	}
	
	public static <T,U> BiConsumer<T, U> biConsumExp(ExceptionableBiConsumer<T, U> consumer, 
			Consumer<Try<Pair<T, U>,Void>> errorProcess) {
		return (t, u) -> {
			try {
				consumer.accept(t, u);
			} catch (Exception e) {
				errorProcess.accept(Try.failure(Pair.of(t, u), e));
			}
		};
	}
	
	public static <T> Predicate<T> predExp(ExceptionablePredicate<T> predicate, 
			Function<Try<T, Void>, Boolean> errorProcess) {
		return t -> {
			try {
				return predicate.test(t);
			} catch (Exception e) {
				return errorProcess.apply(Try.failure(t, e));
			}
		};
	}
	
	public static <T, U> BiPredicate<T, U> biPredExp(ExceptionableBiPredicate<T, U> predicate, 
			Function<Try<Pair<T, U>, Void>, Boolean> errorProcess) {
		return (t, u) -> {
			try {
				return predicate.test(t, u);
			} catch (Exception e) {
				return errorProcess.apply(Try.failure(Pair.of(t, u), e));
			}
		};
	}
}
