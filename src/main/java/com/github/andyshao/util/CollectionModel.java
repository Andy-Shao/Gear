package com.github.andyshao.util;

import java.lang.reflect.Array;
import java.util.Collection;

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
    public default boolean isEmpty() {
        return this.size() == 0 ? true : false;
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

    @Override
    public default boolean containsAll(Collection<?> c) {
        boolean result = true;
        for(E e : this){
            
        }
        return result;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }
}
