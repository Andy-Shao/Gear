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
public class CharArrayWrapper implements ArrayWrapper {
    private final char[] array;

    public CharArrayWrapper(char[] array) {
        this.array = array;
    }

    @Override
    public char[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Character get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getMark() {
        // TODO Auto-generated method stub
        return 0;
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
    public void mark() {
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
    public Character put(Object value , int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
