package com.github.andyshao.lang;

import java.math.BigInteger;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 5, 2015<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 * @param <ARRAY> array type
 */
public interface LongWrapper<ARRAY> {
    public static final LongWrapper<byte[]> BYTE_LONG_WRAPPER = new ByteLongWrapper();
    public static final LongWrapper<char[]> CHAR_LONG_WRAPPER = new CharLongWrapper();
    public static final LongWrapper<short[]> SHORT_LONG_WRAPPER = new ShortLongWrapper();
    public static final LongWrapper<int[]> INT_LONG_WRAPPER = new IntLongWrapper();
    public static final LongWrapper<long[]> LONG_LONG_WRAPPER = new LongLongWrapper();

    public long getLong(ARRAY array, BigInteger position);
    public void setLong(ARRAY array, BigInteger position, long l);
    public BigInteger size(ARRAY array);
}
