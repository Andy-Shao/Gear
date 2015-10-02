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
public class DoubleArrayWrapper implements ArrayWrapper {
    private final double[] array;

    public DoubleArrayWrapper(double[] array) {
        this.array = array;
    }

    @Override
    public double[] array() {
        return this.array;
    }

    @Override
    public int capacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Double get(int index) {
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
    public Double put(Object value , int index) {
        // TODO Auto-generated method stub
        return null;
    }

}
