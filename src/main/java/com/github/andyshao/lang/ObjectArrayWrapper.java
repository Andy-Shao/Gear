package com.github.andyshao.lang;

import com.github.andyshao.reflect.ArrayOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> the element of array's type
 */
public class ObjectArrayWrapper<T> extends ArrayWrapperModel implements ArrayWrapper {
    /**
     * 
     */
    private static final long serialVersionUID = 5377791899938965809L;
    private final T[] array;

    public ObjectArrayWrapper(T[] array) {
        this.array = array;
        this.position = 0;
        this.limit = this.array.length;
        this.mark = 0;
    }

    @Override
    public T[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new ObjectArrayWrapper<T>(ArrayOperation.backup(this.array()));
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public ArrayWrapper duplicate() {
        ArrayWrapper result = new ObjectArrayWrapper<T>(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public T get(int index) {
        return this.array()[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T put(Object value , int index) {
        T result = this.array()[index];
        this.array()[index] = (T) value;
        return result;
    }
}
