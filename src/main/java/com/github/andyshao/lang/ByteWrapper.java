package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Iterator;

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
public interface ByteWrapper<ARRAY> {
    public static final ByteWrapper<byte[]> BYTE_BYTE_WRAPPER = new ByteByteWrapper();
    public static final ByteWrapper<char[]> CHAR_BYTE_WRAPPER = new CharByteWrapper();
    public static final ByteWrapper<int[]> INT_BYTE_WRAPPER = new IntByteWrapper();
    public static final ByteWrapper<long[]> LONG_BYTE_WRAPPER = new LongByteWrapper();
    public static final ByteWrapper<short[]> SHORT_BYTE_WRAPPER = new ShortByteWrapper();

    public byte getByte(final ARRAY array , BigInteger index);

    public default Iterable<Byte> iterable(final ARRAY array) {
        return () -> this.iterator(array);
    }

    public default Iterator<Byte> iterator(final ARRAY array) {
        return new Iterator<Byte>() {
            private volatile BigInteger index = BigInteger.ZERO;
            private final BigInteger size = ByteWrapper.this.size(array);

            @Override
            public boolean hasNext() {
                return this.index.compareTo(this.size) == -1 ? true : false;
            }

            @Override
            public Byte next() {
                Byte result = ByteWrapper.this.getByte(array , this.index);
                this.index = this.index.add(BigInteger.ONE);
                return result;
            }
        };
    }

    public void setByte(final ARRAY array , BigInteger index , byte b);

    public BigInteger size(final ARRAY array);

    public default String toHexString(ARRAY array) {
        StringBuilder builder = new StringBuilder();
        for (Byte b : this.iterable(array))
            builder.append(Convert.BYTE_2_STR.convert(b));
        return builder.toString();
    }
}
