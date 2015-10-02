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
public class FloatArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private final float[] array;

    public FloatArrayWrapper(float[] array) {
        this.array = array;
        this.position = 0;
        this.mark = 0;
        this.limit = this.array.length;
    }

    @Override
    public float[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public Float get(int index) {
        return this.array()[index];
    }

    @Override
    public Float put(Object value , int index) {
        Float result = this.get(index);
        this.array()[index] = Convert.OB_2_FLOAT.convert(value);
        return result;
    }

}
