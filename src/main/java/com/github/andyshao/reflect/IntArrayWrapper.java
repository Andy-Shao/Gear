package com.github.andyshao.reflect;

import java.util.Iterator;

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
public class IntArrayWrapper implements ArrayWrapper<int[]> {
    private final int[] array;

    public IntArrayWrapper(int[] array) {
        this.array = array;
    }

    @Override
    public int[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        return this.array.length;
    }

    @Override
    public Integer get(int index) {
        return this.array[index];
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < IntArrayWrapper.this.capacity();
            }

            @Override
            public Object next() {
                Object result = IntArrayWrapper.this.get(this.index);
                this.index++;
                return result;
            }
        };
    }

    @Override
    public int limit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void limit(int limit) {
        // TODO Auto-generated method stub

    }

    @Override
    public int mark() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void mark(int mark) {
        // TODO Auto-generated method stub

    }

    @Override
    public int position() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void position(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public Integer put(Object value , int index) {
        int tmp = this.array[index];
        this.array[index] = Convert.OB_2_INT.convert(value);
        return tmp;
    }

}
