package com.github.andyshao.util.stream;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	private StreamOperation() {}
	
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	public static <T> Stream<T> distinct(Stream<T> stream, Function<? super T, ?> keyExtractor) {
		return stream.filter(distinctByKey(keyExtractor));
	}
	
	public static <T> Pair<T, Boolean> anyMatch(Predicate<? super T> predicate, Stream<T> stream) {
		Optional<T> op = stream.filter(predicate)
			.findAny();
		return op.isPresent() ? Pair.of(op.get(), true) : Pair.of(null, false);
	}
	
	public static <T> Pair<List<T>, Boolean> allOfMatched(Predicate<? super T> predicate, Stream<T> stream) {
		List<T> ls = stream.filter(predicate)
			.collect(Collectors.toList());
		return CollectionOperation.isEmptyOrNull(ls) ? Pair.of(ls, false) : Pair.of(ls, true);
	}
}
