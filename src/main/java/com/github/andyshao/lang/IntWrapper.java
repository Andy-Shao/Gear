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
    /**Byte int wrapper*/
    public static final IntWrapper<byte[]> BYTE_INT_WRAPPER = new ByteIntWrapper();
    /**Char int wrapper*/
    public static final IntWrapper<char[]> CHAR_INT_WRAPPER = new CharIntWrapper();
    /**int to int wrapper*/
    public static final IntWrapper<int[]> INT_INT_WRAPPER = new IntIntWrapper();
    /**long int wrapper*/
    public static final IntWrapper<long[]> LONG_INT_WRAPPER = new LongIntWrapper();
    /**short int wrapper*/
    public static final IntWrapper<short[]> SHORT_INT_WRAPPER = new ShortIntWrapper();

    /**
     * Get int value
     * @param array the array
     * @param position the position
     * @return the answer
     */
    public int getInt(ARRAY array , BigInteger position);

    /**
     * Default int {@link Iterator}
     * @param array the array
     * @return {@link Iterator}
     */
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

    /**
     * set int value
     * @param array the array
     * @param position the position
     * @param i the setting value
     */
    public void setInt(ARRAY array , BigInteger position , int i);

    /**
     * size of the array
     * @param array the array
     * @return the size number
     */
    public BigInteger size(ARRAY array);

}
