package com.github.andyshao.lang;

import java.math.BigInteger;

/**
 * Wrap any kinds of object or array. Operate them as a byte array.
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <ARRAY> the array type
 */
public interface ByteWrapper<ARRAY> extends Iterable<Byte> {
    public static final ByteWrapper<byte[]> BYTE_BYTE_WRAPPER = new ByteByteWrapper();
    public static final ByteWrapper<char[]> CHAR_BYTE_WRAPPER = new CharByteWrapper();
    public static final ByteWrapper<int[]> INT_BYTE_WRAPPER = new IntegerByteWrapper();
    public static final ByteWrapper<long[]> LONG_BYTE_WRAPPER = new LongByteWrapper();
    public static final ByteWrapper<short[]> SHORT_BYTE_WRAPPER = new ShortByteWrapper();

    public byte getByte(final ARRAY array , BigInteger index);

    public void setByte(final ARRAY array , BigInteger index , byte b);

    public BigInteger size(final ARRAY array);
}
