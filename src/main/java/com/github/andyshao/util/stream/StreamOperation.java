package com.github.andyshao.util.stream;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.github.andyshao.util.CollectionOperation;

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
 */
public final class StreamOperation {
	public static <T> Pair<List<T>, Boolean> allOfMatched(Stream<T> stream, Predicate<? super T> predicate) {
		List<T> ls = stream.filter(predicate)
			.collect(Collectors.toList());
		return CollectionOperation.isEmptyOrNull(ls) ? Pair.of(ls, false) : Pair.of(ls, true);
	}
	
	public static <T> Pair<T, Boolean> anyMatch(Stream<T> stream, Predicate<? super T> predicate) {
		Optional<T> op = stream.filter(predicate)
			.findAny();
		return op.isPresent() ? Pair.of(op.get(), true) : Pair.of(null, false);
	}
	
	public static <T> Stream<T> distinct(Stream<T> stream, Function<? super T, ?> keyExtractor) {
		return stream.filter(distinctByKey(keyExtractor));
	}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	public static <T> Pair<T, Boolean> firstMatch(Stream<T> stream, Predicate<? super T> predicate) {
		Optional<T> op = stream.filter(predicate)
			.findFirst();
		return op.isPresent() ? Pair.of(op.get(), true) : Pair.of(null, false);
	}

	public static <T, S extends BaseStream<T, S>> Stream<T> valueOf(BaseStream<T, S> stream) {
		return valueOf(stream, false);
	}

	public static <T, S extends BaseStream<T, S>> Stream<T> valueOf(BaseStream<T, S> stream, boolean parallel) {
		return StreamSupport.stream(stream.spliterator(), parallel);
	}
	
	private StreamOperation() {}
}
