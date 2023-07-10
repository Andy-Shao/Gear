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
public interface LongWrapper<ARRAY> {
    /**byte long wrapper*/
    public static final LongWrapper<byte[]> BYTE_LONG_WRAPPER = new ByteLongWrapper();
    /**char long wrapper*/
    public static final LongWrapper<char[]> CHAR_LONG_WRAPPER = new CharLongWrapper();
    /**int long wrapper*/
    public static final LongWrapper<int[]> INT_LONG_WRAPPER = new IntLongWrapper();
    /**long to long wrapper*/
    public static final LongWrapper<long[]> LONG_LONG_WRAPPER = new LongLongWrapper();
    /**short long wrapper*/
    public static final LongWrapper<short[]> SHORT_LONG_WRAPPER = new ShortLongWrapper();

    /**
     * get long from array
     * @param array the array
     * @param position the position
     * @return the value
     */
    public long getLong(ARRAY array , BigInteger position);

    /**
     * Default long {@link Iterator}
     * @param array the array
     * @return {@link Iterator}
     */
    public default Iterator<Long> iterator(ARRAY array) {
        return new Iterator<Long>() {
            private volatile BigInteger index = BigInteger.ZERO;
            private final BigInteger size = LongWrapper.this.size(array);

            @Override
            public boolean hasNext() {
                return this.index.compareTo(this.size) == -1;
            }

            @Override
            public Long next() {
                long l = LongWrapper.this.getLong(array , this.index);
                this.index = this.index.add(BigInteger.ONE);
                return l;
            }
        };
    }

    /**
     * set long value
     * @param array the array
     * @param position the position
     * @param l long value
     */
    public void setLong(ARRAY array , BigInteger position , long l);

    /**
     * the size of the array
     * @param array the array
     * @return the size number
     */
    public BigInteger size(ARRAY array);
}
