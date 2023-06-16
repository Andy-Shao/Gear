package com.github.andyshao.util;

import com.github.andyshao.lang.ArrayWrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

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
    public static <E> Collection<E> of(Collection<E> col, E item){
        col.add(item);
        return col;
    }

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

    public static <E, T extends Collection<E>> T or(Collection<E> left, Collection<E> right, Supplier<T> supplier) {
        T ret = supplier.get();
        if(Objects.isNull(left) && Objects.isNull(right)) {
            return ret;
        } else if(Objects.isNull(left)) {
            ret.addAll(right);
            return ret;
        } else if(Objects.isNull(right)) {
            ret.addAll(left);
            return ret;
        } else {
            ret.addAll(left);
            ret.addAll(right);
            return ret;
        }
    }

    public static <E, T extends Collection<E>> T and(Collection<E> left, Collection<E> right, Supplier<T> supplier) {
        T ret = supplier.get();
        if(Objects.isNull(left) || Objects.isNull(right)) {
            return ret;
        }
        left.stream()
                .filter(right::contains)
                .forEach(ret::add);
        return ret;
    }

    /**
     * return a collection which includes items that are included in left but
     * are not included in right.
     * @param left
     * @param right
     * @param supplier
     * @return
     * @param <E>
     * @param <T>
     */
    public static <E, T extends Collection<E>> T not(Collection<E> left, Collection<E> right, Supplier<T> supplier) {
        T ret = supplier.get();
        if(Objects.isNull(left)) {
            return ret;
        } else if(Objects.isNull(right)) {
            ret.addAll(left);
            return ret;
        } else {
            left.stream()
                    .filter(it -> !right.contains(it))
                    .forEach(ret::add);
            return ret;
        }
    }

    public static <E, T extends Collection<E>> T xor(Collection<E> left, Collection<E> right, Supplier<T> supplier) {
        return or(not(left, right, supplier), not(right, left, supplier), supplier);
    }

    private CollectionOperation() {
        throw new AssertionError("No " + CollectionOperation.class + " instance for you!");
    }
}
