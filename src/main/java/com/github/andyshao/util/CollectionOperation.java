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
    /**
     * of operation
     * @param col {@link Collection}
     * @param item the member of {@link Collection}
     * @return origin {@link Collection}
     * @param <E> data type
     */
    public static <E> Collection<E> of(Collection<E> col, E item){
        col.add(item);
        return col;
    }

    /**
     * range
     * @param start start point (inclusive)
     * @param end end point (exclusive)
     * @param step add step
     * @param fun {@link Function}
     * @return {@link Iterator}
     * @param <E> data type
     */
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

    /**
     * add all
     * @param collection {@link Collection}
     * @param array {@link com.github.andyshao.lang.ArrayType} array
     * @param <E> the type of {@link Collection}'s members'
     * @param <ARRAY> array type
     */
    public static <E , ARRAY> void addAll(Collection<E> collection , ARRAY array) {
        CollectionOperation.addAll(collection , ArrayWrapper.wrap(array));
    }

    /**
     * add all
     * @param collection {@link Collection}
     * @param array array type
     * @param <E> the type of {@link Collection}'s members'
     */
    @SuppressWarnings("unchecked")
    public static <E> void addAll(Collection<E> collection , ArrayWrapper array) {
        for (Object item : array) collection.add((E) item);
    }

    /**
     * is empty or null
     * @param collection {@link Collection}
     * @return if it is then true
     * @param <E> the type of {@link Collection}'s members'
     */
    public static <E> boolean isEmptyOrNull(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * or operation
     * @param left left {@link Collection}
     * @param right right {@link Collection}
     * @param supplier {@link Supplier}
     * @return {@link Collection}
     * @param <E> member type
     * @param <T> {@link Collection} type
     */
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

    /**
     * and operation
     * @param left left {@link Collection}
     * @param right right {@link Collection}
     * @param supplier {@link Supplier}
     * @return {@link Collection}
     * @param <E> member type
     * @param <T> {@link Collection} type
     */
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
     * @param left left {@link Collection}
     * @param right right {@link Collection}
     * @param supplier {@link Supplier}
     * @return {@link Collection}
     * @param <E> member type
     * @param <T> {@link Collection} type
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

    /**
     * xor operation
     * @param left left {@link Collection}
     * @param right right {@link Collection}
     * @param supplier {@link Supplier}
     * @return {@link Collection}
     * @param <E> member type
     * @param <T> {@link Collection} type
     */
    public static <E, T extends Collection<E>> T xor(Collection<E> left, Collection<E> right, Supplier<T> supplier) {
        return or(not(left, right, supplier), not(right, left, supplier), supplier);
    }

    private CollectionOperation() {
        throw new AssertionError("No " + CollectionOperation.class + " instance for you!");
    }
}
