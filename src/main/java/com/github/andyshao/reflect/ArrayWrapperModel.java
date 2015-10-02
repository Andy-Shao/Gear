package com.github.andyshao.reflect;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public abstract class ArrayWrapperModel implements ArrayWrapper {
    protected int limit;
    protected int mark;
    protected int position;

    @Override
    public int getMark() {
        return this.mark;
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public void limit(int limit) {
        if (limit < this.position() || limit < 0) throw new IllegalArgumentException();
        this.limit = limit;
    }

    @Override
    public void mark() {
        this.mark = this.position();
    }

    @Override
    public int position() {
        return this.position;
    }

    @Override
    public void position(int position) {
        if (position < 0 || position > this.limit()) throw new IllegalArgumentException();
        this.position = position;
    }
}
