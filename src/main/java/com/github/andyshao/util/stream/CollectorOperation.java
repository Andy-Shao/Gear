package com.github.andyshao.util.stream;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.github.andyshao.data.structure.SimpleQueue;
import com.github.andyshao.lang.AutoIncreaseArray;
import com.github.andyshao.lang.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 24, 2019<br>
 * Encoding: UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class CollectorOperation {
	private CollectorOperation() {}
	
	public static <T, R> Collector<T, ?, R[]> toArray(Convert<T, R> convert, R[] array) {
		return CollectorImpl.<T, AutoIncreaseArray<R>, R[]>builder()
				.withSupplier(AutoIncreaseArray::new)
				.withAccumulator((arr, it) -> arr.add(convert.convert(it)))
				.withCombiner((l, r) -> {
					l.addAll(r);
					return l;
				})
				.withFinisher(t -> t.toArray(array))
				.withCharacteristics(CollectorImpl.CH_ID)
				.build();
	}
	
	@SuppressWarnings("unchecked")
	public static <T, R> Collector<T, ?, R[]> toArray(Convert<T, R> convert, Class<R> componentType) {
		return CollectorImpl.<T, AutoIncreaseArray<R>, R[]>builder()
				.withSupplier(AutoIncreaseArray::new)
				.withAccumulator((arr, it) -> arr.add(convert.convert(it)))
				.withCombiner((l, r) -> {
					l.addAll(r);
					return l;
				})
				.withFinisher(t -> t.toArray((R[]) Array.newInstance(componentType, t.size())))
				.withCharacteristics(CollectorImpl.CH_ID)
				.build();
	}
	
	public static <T> Collector<T, ?, T[]> toArray(Class<T> componentType) {
		return toArray(Convert.identity(), componentType);
	}
			
	public static <T, R> Collector<T, ?, List<R>> toList(Convert<T, R> convert) {
//		return CollectorImpl.<T, List<R>>idBuilder()
//				.withSupplier(ArrayList::new)
//				.withAccumulator((ls, it) -> ls.add(covert.convert(it)))
//				.withCombiner((l, r) -> {
//					l.addAll(r);
//					return l;
//				})
//				.build();
		return Collectors.mapping(it -> convert.convert(it), Collectors.toList());
	}
	
	public static <T, R> Collector<T, ?, List<R>> toListConcurrent(Convert<T, R> convert) {
		return CollectorImpl
				.<T, Queue<R>, List<R>>builder()
				.withSupplier(LinkedBlockingQueue::new)
				.withAccumulator((set, it) -> set.add(convert.convert(it)))
				.withCombiner((l, r) -> {
					l.addAll(r);
					return l;
				})
				.withFinisher(set -> new ArrayList<R>(set))
				.withCharacteristics(CollectorImpl.CH_CONCURRENT_NOID)
				.build();
	}
	
	public static <T> Collector<T, ?, List<T>> toListConcurrent() {
		return toListConcurrent(Convert.identity());
	}
	
	public static <T, R> Collector<T, ?, Set<R>> toSet(Convert<T, R> convert) {
//		return CollectorImpl.<T, Set<R>>idBuilder()
//				.withSupplier(HashSet::new)
//				.withAccumulator((set, it) -> set.add(convert.convert(it)))
//				.withCombiner((l, r) -> {
//					l.addAll(r);
//					return l;
//				})
//				.build();
		return Collectors.mapping(it -> convert.convert(it), Collectors.toSet());
	}
	
	public static <T> Collector<T, ?, Set<T>> toSet() {
		return toSet(Convert.identity());
	}
	
	public static <T, R> Collector<T, ?, ConcurrentSkipListSet<R>> toSetConcurrent(
			Convert<T, R> convert) {
		return CollectorImpl.<T, ConcurrentSkipListSet<R>>idCurrentBuilder()
				.withSupplier(ConcurrentSkipListSet::new)
				.withAccumulator((set, it) -> set.add(convert.convert(it)))
				.withCombiner((l, r) -> {
					l.addAll(r);
					return l;
				})
				.build();
	}
	
	public static <T> Collector<T, ?, ConcurrentSkipListSet<T>> toSetConcurrent() {
		return toSetConcurrent(Convert.identity());
	}
	
	public static <T, R> Collector<T, ?, Queue<R>> toQueue(Convert<T, R> convert) {
		return CollectorImpl.<T, Queue<R>>idBuilder()
				.withSupplier(SimpleQueue::new)
				.withAccumulator((q, it) -> q.offer(convert.convert(it)))
				.withCombiner((l, r) -> {
					l.addAll(r);
					return l;
				})
				.build();
	}
	
	public static <T> Collector<T, ?, Queue<T>> toQueue() {
		return toQueue(Convert.identity());
	}
	
	public static <T, R> Collector<T, ?, Queue<R>> toQueueConcurrent(Convert<T, R> convert) {
		return CollectorImpl.<T, Queue<R>>idCurrentBuilder()
				.withSupplier(LinkedBlockingQueue::new)
				.withAccumulator((q, it) -> q.offer(convert.convert(it)))
				.withCombiner((l, r) -> {
					l.addAll(r);
					return l;
				})
				.build();
	}
	
	public static <T> Collector<T, ?, Queue<T>> toQueueConcurrent() {
		return toQueue(Convert.identity());
	}
}
