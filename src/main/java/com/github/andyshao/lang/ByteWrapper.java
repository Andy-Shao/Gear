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
    /**Byte byte wrapper*/
    public static final ByteWrapper<byte[]> BYTE_BYTE_WRAPPER = new ByteByteWrapper();
    /**Char byte wrapper*/
    public static final ByteWrapper<char[]> CHAR_BYTE_WRAPPER = new CharByteWrapper();
    /**int byte wrapper*/
    public static final ByteWrapper<int[]> INT_BYTE_WRAPPER = new IntByteWrapper();
    /**long byte wrapper*/
    public static final ByteWrapper<long[]> LONG_BYTE_WRAPPER = new LongByteWrapper();
    /**short byte wrapper*/
    public static final ByteWrapper<short[]> SHORT_BYTE_WRAPPER = new ShortByteWrapper();

    /**
     * get byte
     * @param array the array
     * @param index the position
     * @return the answer
     */
    public byte getByte(final ARRAY array , BigInteger index);

    /**
     * get {@link Iterator}
     * @param array the array
     * @return {@link Iterator}
     */
    public default Iterator<Byte> iterator(final ARRAY array) {
        return new Iterator<Byte>() {
            private volatile BigInteger index = BigInteger.ZERO;
            private final BigInteger size = ByteWrapper.this.size(array);

            @Override
            public boolean hasNext() {
                return this.index.compareTo(this.size) == -1;
            }

            @Override
            public Byte next() {
                Byte result = ByteWrapper.this.getByte(array , this.index);
                this.index = this.index.add(BigInteger.ONE);
                return result;
            }
        };
    }

    /**
     * set byte
     * @param array the array
     * @param index the position
     * @param b the byte
     */
    public void setByte(final ARRAY array , BigInteger index , byte b);

    /**
     * the size of the array
     * @param array array
     * @return the size
     */
    public BigInteger size(final ARRAY array);
}
