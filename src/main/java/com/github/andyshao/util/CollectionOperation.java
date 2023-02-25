package com.github.andyshao.util;

import com.github.andyshao.lang.ArrayWrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;

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
    public static <E> Iterator<E> range(int start, int end, int step, Function<Integer, E> fun){
        return new Iterator<E>() {
            private int start;
            Iterator<E> setStart(int st){
                start = st;
                return this;
            }
            @Override
            public boolean hasNext() {
                return start < end;
            }

            @Override
            public E next() {
                E ret = fun.apply(start);
                start += step;
                return ret;
            }
        }.setStart(start);
    }
    public static <E , ARRAY> void addAll(Collection<E> collection , ARRAY array) {
        CollectionOperation.addAll(collection , ArrayWrapper.wrap(array));
    }

    @SuppressWarnings("unchecked")
    public static <E> void addAll(Collection<E> collection , ArrayWrapper array) {
        for (Object item : array) collection.add((E) item);
    }
    
    public static <E> boolean isEmptyOrNull(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    private CollectionOperation() {
        throw new AssertionError("No " + CollectionOperation.class + " instance for you!");
    }
}
