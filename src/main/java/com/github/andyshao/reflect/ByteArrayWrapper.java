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
 *
 */
public class ByteArrayWrapper implements ArrayWrapper<byte[]> {
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
    public Iterator<Object> iterator() {
        // TODO Auto-generated method stub
        return null;
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
    public Byte put(Object value , int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
