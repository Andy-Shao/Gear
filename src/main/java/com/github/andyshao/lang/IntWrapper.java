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

    public default Iterator<Integer> iterator(ARRAY array) {
        return new Iterator<Integer>() {
            private volatile BigInteger index = BigInteger.ZERO;
            private final BigInteger size = IntWrapper.this.size(array);

            @Override
            public boolean hasNext() {
                return this.index.compareTo(this.size) == -1;
            }

            @Override
            public Integer next() {
                int i = IntWrapper.this.getInt(array , this.index);
                this.index = this.index.add(BigInteger.ONE);
                return i;
            }
        };
    }

    public void setInt(ARRAY array , BigInteger position , int i);

    public BigInteger size(ARRAY array);

}
