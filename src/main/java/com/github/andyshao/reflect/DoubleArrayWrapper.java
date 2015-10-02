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
public class DoubleArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private final double[] array;

    public DoubleArrayWrapper(double[] array) {
        this.array = array;
        this.position = 0;
        this.mark = 0;
        this.limit = this.array.length;
    }

    @Override
    public double[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public Double get(int index) {
        return this.array()[index];
    }

    @Override
    public Double put(Object value , int index) {
        Double result = this.get(index);
        this.array()[index] = Convert.OB_2_DOUBLE.convert(value);
        return result;
    }

}