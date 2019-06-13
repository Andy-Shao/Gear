package com.github.andyshao.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Supplier;

import com.github.andyshao.data.structure.SimpleQueue;
import com.github.andyshao.lang.ArrayWrapper;
import com.github.andyshao.lang.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 30, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class CollectionOperation {
	public static final <E> Convert<Collection<E>, Queue<E>> toQueue() {
		return toQueue(SimpleQueue::new);
	}
	
	public static final <E> Convert<Collection<E>, Queue<E>> toQueue(Supplier<Queue<E>> supplier) {
		return input -> {
			Queue<E> queue = supplier.get();
			queue.addAll(input);
			return queue;
		};
	}
	
	public static final <E> Convert<Collection<E>, List<E>> toList() {
		return toList(ArrayList::new);
	}
	
	public static final <E> Convert<Collection<E>, List<E>> toList(Supplier<List<E>> supplier) {
		return input -> {
			List<E> list = supplier.get();
			list.addAll(input);
			return list;
		};
	}
	
	public static final <E> Convert<Collection<E>, Set<E>> toSet(Supplier<Set<E>> supplier) {
		return input -> {
			Set<E> set = supplier.get();
			set.addAll(input);
			return set;
		};
	}
	
	public static final <E> Convert<Collection<E>, Set<E>> toSet() {
		return toSet(HashSet::new);
	}
	
    public static <E , ARRAY> void addAll(Collection<E> collection , ARRAY array) {
        CollectionOperation.addAll(collection , ArrayWrapper.wrap(array));
    }

    @SuppressWarnings("unchecked")
    public static <E> void addAll(Collection<E> collection , ArrayWrapper array) {
        for (Object item : array)
            collection.add((E) item);
    }
    
    public static <E> boolean isEmptyOrNull(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    private CollectionOperation() {
        throw new AssertionError("No " + CollectionOperation.class + " instance for you!");
    }
}
