package com.github.andyshao.util;

import java.util.Collection;

import com.github.andyshao.lang.ArrayWrapper;

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
    public static <E , ARRAY> void addAll(Collection<E> collection , ARRAY array) {
        CollectionOperation.addAll(collection , ArrayWrapper.wrap(array));
    }

    @SuppressWarnings("unchecked")
    public static <E> void addAll(Collection<E> collection , ArrayWrapper array) {
        for (Object item : array)
            collection.add((E) item);
    }

    public CollectionOperation() {
        throw new AssertionError("No " + CollectionOperation.class + " instance for you!");
    }
}
