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
public interface ShortWrapper<ARRAY> {
    public static final ShortWrapper<byte[]> BYTE_SHORT_WRAPPER = new ByteShortWrapper();
    public static final ShortWrapper<char[]> CHAR_SHORT_WRAPPER = new CharShortWrapper();
    public static final ShortWrapper<int[]> INT_SHORT_WRAPPER = new IntShortWrapper();
    public static final ShortWrapper<long[]> LONG_SHORT_WRAPPER = new LongShortWrapper();
    public static final ShortWrapper<short[]> SHORT_SHORT_WRAPPER = new ShortShortWrapper();

    public short getShort(ARRAY array , BigInteger position);

    public default Iterable<Short> iterable(ARRAY array) {
        return () -> this.iterator(array);
    }

    public default Iterator<Short> iterator(ARRAY array) {
        //TODO
        return null;
    }

    public void setShort(ARRAY array , BigInteger position , short s);

    public BigInteger size(ARRAY array);

}
