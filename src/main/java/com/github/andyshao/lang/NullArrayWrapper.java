package com.github.andyshao.lang;

import java.util.stream.Stream;

/**
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright(c) 2020/9/1
 * Encoding: UNIX UTF-8
 *
 * @author Andy.Shao
 */
public final class NullArrayWrapper implements ArrayWrapper {

    @Override
    public Object array() {
        return null;
    }

    @Override
    public ArrayWrapper backup() {
        return new NullArrayWrapper();
    }

    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public ArrayWrapper duplicate() {
        return new NullArrayWrapper();
    }

    @Override
    public Object get(int index) throws ArrayIndexOutOfBoundsException {
        return null;
    }

    @Override
    public int limit() {
        return 0;
    }

    @Override
    public void limit(int limit) throws ArrayIndexOutOfBoundsException {

    }

    @Override
    public void mark() {

    }

    @Override
    public void markLimit() {

    }

    @Override
    public int position() {
        return 0;
    }

    @Override
    public void position(int position) throws ArrayIndexOutOfBoundsException {

    }

    @Override
    public Object put(Object value, int index) throws ArrayIndexOutOfBoundsException {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public void resetLimit() {

    }

    @Override
    public Stream<?> stream() {
        return Stream.empty();
    }
}
