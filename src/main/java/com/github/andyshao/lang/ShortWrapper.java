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
    /**Byte short wrapper*/
    public static final ShortWrapper<byte[]> BYTE_SHORT_WRAPPER = new ByteShortWrapper();
    /**char short wrapper*/
    public static final ShortWrapper<char[]> CHAR_SHORT_WRAPPER = new CharShortWrapper();
    /**int short wrapper*/
    public static final ShortWrapper<int[]> INT_SHORT_WRAPPER = new IntShortWrapper();
    /**long short wrapper*/
    public static final ShortWrapper<long[]> LONG_SHORT_WRAPPER = new LongShortWrapper();
    /**short to short wrapper*/
    public static final ShortWrapper<short[]> SHORT_SHORT_WRAPPER = new ShortShortWrapper();

    /**
     * get short from array
     * @param array the array
     * @param position the position
     * @return the answer
     */
    public short getShort(ARRAY array , BigInteger position);

    /**
     * default short {@link Iterator}
     * @param array the array
     * @return the answer
     */
    public default Iterator<Short> iterator(final ARRAY array) {
        return new Iterator<Short>() {
            private volatile BigInteger index = BigInteger.ZERO;
            private final BigInteger size = ShortWrapper.this.size(array);

            @Override
            public boolean hasNext() {
                return this.index.compareTo(this.size) == -1;
            }

            @Override
            public Short next() {
                short s = ShortWrapper.this.getShort(array , this.index);
                this.index = this.index.add(BigInteger.ONE);
                return s;
            }
        };
    }

    /**
     * set short into array
     * @param array the array
     * @param position the position
     * @param s set value
     */
    public void setShort(ARRAY array , BigInteger position , short s);

    /**
     * the size from the array
     * @param array the array
     * @return the size number
     */
    public BigInteger size(ARRAY array);

}
