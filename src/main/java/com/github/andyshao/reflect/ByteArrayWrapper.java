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
    }

    @Override
    public byte[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Byte get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Byte put(Object value , int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
