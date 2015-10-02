package com.github.andyshao.reflect;

import com.github.andyshao.lang.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 1, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class IntArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private final int[] array;

    public IntArrayWrapper(int[] array) {
        this.array = array;
        this.position = 0;
        this.limit = this.array.length;
        this.mark = 0;
    }

    @Override
    public int[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public Integer get(int index) {
        return this.array()[index];
    }

    @Override
    public Integer put(Object value , int index) {
        int tmp = this.array()[index];
        this.array()[index] = Convert.OB_2_INT.convert(value);
        return tmp;
    }

}
