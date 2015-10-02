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
 */
public class LongArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private final long[] array;

    public LongArrayWrapper(long[] array) {
        this.array = array;
        this.mark = 0;
        this.position = 0;
        this.limit = this.array.length;
    }

    @Override
    public long[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new LongArrayWrapper(ArrayOperation.backup(this.array()));
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
        ArrayWrapper result = new LongArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Long get(int index) {
        return this.array()[index];
    }

    @Override
    public Long put(Object value , int index) {
        Long result = this.get(index);
        this.array()[index] = Convert.OB_2_LONG.convert(value);
        return result;
    }

}
