package com.github.andyshao.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 16, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <E> element
 */
public interface CollectionModel<E> extends Collection<E> {
    @Override
    public default boolean addAll(Collection<? extends E> c) {
        boolean result = true;
        for (E e : c)
            result = result && this.add(e);
        return result;
    }

    @Override
    public default boolean contains(Object o) {
        for (E e : this)
            if (Objects.equals(e , o)) return true;
        return false;
    }

    default int indexOf(Object o) {
        int index = 0;
        for(E e : this) {
            if(Objects.equals(e, o)) return index;
            index++;
        }
        return -1;
    }

    default int lastIndexOf(Object o) {
        int index = 0;
        for(E e : this) {
            index++;
            if(Objects.equals(e, o)) return this.size() - index;
        }
        return -1;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        for (Object o : c)
            if (!this.contains(o)) return false;
        return true;
    }

    @Override
    public default boolean isEmpty() {
        return this.size() == 0 ? true : false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        boolean result = true;
        for (Object o : c)
            result = result && this.remove(o);
        return result;
    }

    @Override
    public default Object[] toArray() {
        Object[] array = new Object[this.size()];
        int index = 0;
        for (E d : this)
            array[index++] = d;
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public default <T> T[] toArray(T[] a) {
        T[] result = null;
        if (a.length >= this.size()) result = a;
        else result = (T[]) Array.newInstance(a.getClass().getComponentType() , this.size());

        int index = 0;
        for (E d : this)
            result[index++] = (T) d;
        return result;
    }
}
