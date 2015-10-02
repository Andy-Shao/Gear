package com.github.andyshao.lang;

import com.github.andyshao.reflect.ArrayOperation;

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
public class CharArrayWrapper extends ArrayWrapperModel implements ArrayWrapper {
    private final char[] array;

    public CharArrayWrapper(char[] array) {
        this.array = array;
        this.limit = this.array.length;
        this.mark = 0;
        this.position = 0;
    }

    @Override
    public char[] array() {
        return this.array;
    }

    @Override
    public ArrayWrapper backup() {
        ArrayWrapper result = new CharArrayWrapper(ArrayOperation.backup(this.array()));
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public int capacity() {
        return this.array().length;
    }

    @Override
    public ArrayWrapper duplicate() {
        ArrayWrapper result = new CharArrayWrapper(this.array());
        result.position(this.position());
        result.limit(this.limit());
        result.mark();
        return result;
    }

    @Override
    public Character get(int index) {
        return this.array()[index];
    }

    @Override
    public Character put(Object value , int index) {
        Character result = this.get(index);
        this.array()[index] = Convert.OB_2_CHAR.convert(value);
        return result;
    }

}
