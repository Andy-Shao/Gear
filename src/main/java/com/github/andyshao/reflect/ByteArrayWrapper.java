package com.github.andyshao.reflect;

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
public class ByteArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private final byte[] array;

    public ByteArrayWrapper(byte[] array) {
        this.array = array;
        this.limit = this.array.length;
        this.position = 0;
        this.mark = 0;
    }

    @Override
    public byte[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public Byte get(int index) {
        return this.array()[index];
    }

    @Override
    public Byte put(Object value , int index) {
        Byte result = this.get(index);
        this.array()[index] = (Byte) value;
        return result;
    }

}
