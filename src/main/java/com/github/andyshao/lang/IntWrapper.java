package com.github.andyshao.lang;

import java.math.BigInteger;

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

    public void setInt(ARRAY array , BigInteger position , int i);

    public BigInteger size(ARRAY array);
}
