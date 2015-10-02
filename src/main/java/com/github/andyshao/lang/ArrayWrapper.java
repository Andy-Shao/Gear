package com.github.andyshao.lang;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.github.andyshao.reflect.ArrayOperation;

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
    public static <ARRAY> ArrayWrapper newInstance(Class<ARRAY> arrayType , int length) {
        if (!arrayType.isArray()) throw new IllegalArgumentException();
        @SuppressWarnings("unchecked")
        ARRAY array = (ARRAY) Array.newInstance(arrayType.getComponentType() , length);
        return ArrayWrapper.wrap(array);
    }

    @SuppressWarnings("unchecked")
    public static <ARRAY , E> ArrayWrapper wrap(ARRAY array) {
        if (!array.getClass().isArray()) throw new IllegalArgumentException();
        if (int[].class.isInstance(array)) return new IntArrayWrapper((int[]) array);
        else if (byte[].class.isInstance(array)) return new ByteArrayWrapper((byte[]) array);
        else if (char[].class.isInstance(array)) return new CharArrayWrapper((char[]) array);
        else if (short[].class.isInstance(array)) return new ShortArrayWrapper((short[]) array);
        else if (float[].class.isInstance(array)) return new FloatArrayWrapper((float[]) array);
        else if (double[].class.isInstance(array)) return new DoubleArrayWrapper((double[]) array);
        else if (long[].class.isInstance(array)) return new LongArrayWrapper((long[]) array);
        else return new ObjectArrayWrapper<E>((E[]) array);
    }

    public Object array();

    public ArrayWrapper backup();

    public int capacity();

    public default <ARRAY> ARRAY clip(int start , int end , Class<ARRAY> arrayType) {
        ArrayWrapper tmp = this.duplicate();
        tmp.position(start);
        tmp.limit(end);
        return tmp.usedArray(arrayType);
    }

    public ArrayWrapper duplicate();

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

    @SuppressWarnings("unchecked")
    public default <ARRAY> ARRAY usedArray(Class<ARRAY> arrayType) {
        Object usedArray = ArrayOperation.splitArray(this.array() , this.position() , this.limit());
        if (arrayType.isInstance(usedArray)) return (ARRAY) usedArray;
        else return ArrayOperation.pack_unpack(usedArray , arrayType);
    }
}
