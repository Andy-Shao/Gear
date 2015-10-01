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
 * @param <ARRAY> array
 */
public interface ArrayWrapper<ARRAY> extends Iterable<Object> {

    public ARRAY array();

    public int capacity();

    public Object get(int index);

    public int limit();

    public void limit(int limit);

    public int mark();

    public void mark(int mark);

    public int position();

    public void position(int position);

    public Object put(Object value , int index);
}
