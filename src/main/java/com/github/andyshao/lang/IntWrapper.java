package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 5, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <ARRAY> array type
 */
public interface IntWrapper<ARRAY> {
    public static final IntWrapper<byte[]> BYTE_INT_WRAPPER = new ByteIntWrapper();
    public static final IntWrapper<char[]> CHAR_INT_WRAPPER = new CharIntWrapper();
    public static final IntWrapper<int[]> INT_INT_WRAPPER = new IntIntWrapper();
    public static final IntWrapper<long[]> LONG_INT_WRAPPER = new LongIntWrapper();
    public static final IntWrapper<short[]> SHORT_INT_WRAPPER = new ShortIntWrapper();

    public int getInt(ARRAY array , BigInteger position);

    public default Iterable<Integer> iterable(ARRAY array) {
        return () -> this.iterator(array);
    }

    public default Iterator<Integer> iterator(ARRAY array) {
        //TODO
        return null;
    }

    public void setInt(ARRAY array , BigInteger position , int i);

    public BigInteger size(ARRAY array);

    public default String toHexString(ARRAY array) {
        //TODO
        return null;
    }
}
