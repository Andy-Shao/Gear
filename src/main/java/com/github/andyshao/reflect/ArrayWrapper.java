package com.github.andyshao.reflect;

import java.util.Iterator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 1, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 */
public interface ArrayWrapper extends Iterable<Object> {

    public Object array();

    public int capacity();

    public Object get(int index);

    public int getMark();

    @Override
    public default Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private int index = ArrayWrapper.this.position();

            @Override
            public boolean hasNext() {
                return this.index < ArrayWrapper.this.limit();
            }

            @Override
            public Object next() {
                Object result = ArrayWrapper.this.get(this.index);
                this.index++;
                return result;
            }
        };
    }

    public default int length() {
        return this.limit() - this.position();
    }

    public int limit();

    public void limit(int limit);

    public void mark();

    public int position();

    public void position(int position);

    public Object put(Object value , int index);
}
