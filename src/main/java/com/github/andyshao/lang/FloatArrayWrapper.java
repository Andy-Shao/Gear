package com.github.andyshao.lang;

import com.github.andyshao.reflect.ArrayOperation;

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
    private static final long serialVersionUID = -3301939189721674721L;
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
    public ArrayWrapper backup() {
        ArrayWrapper result = new FloatArrayWrapper(ArrayOperation.backup(this.array()));
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
        ArrayWrapper result = new FloatArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Float get(int index) {
        return this.array()[this.calculateRealIndex(index)];
    }

    @Override
    public Float put(Object value , int index) {
    	int realIndex = this.calculateRealIndex(index);
        Float result = this.get(realIndex);
        this.array()[realIndex] = Convert.OB_2_FLOAT.convert(value);
        return result;
    }

}
