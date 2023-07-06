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
public interface CharWrapper<ARRAY> {
    /**byte char wrapper*/
    public static final CharWrapper<byte[]> BYTE_CHAR_WRAPPER = new ByteCharWrapper();
    /**char to char wrapper*/
    public static final CharWrapper<char[]> CHAR_CHAR_WRAPPER = new CharCharWrapper();
    /**int char wrapper*/
    public static final CharWrapper<int[]> INT_CHAR_WRAPPER = new IntCharWrapper();
    /**long char wrapper*/
    public static final CharWrapper<long[]> LONG_CHAR_WRAPPER = new LongCharWrapper();
    /**short char wrapper*/
    public static final CharWrapper<short[]> SHORT_CHAR_WRAPPER = new ShortCharWrapper();

    /**
     * get char
     * @param array array
     * @param position position
     * @return the value
     */
    public char getChar(ARRAY array , BigInteger position);

    /**
     * char {@link Iterator}
     * @param array the array
     * @return {@link Iterator}
     */
    public default Iterator<Character> iterator(final ARRAY array) {
        return new Iterator<Character>() {
            private volatile BigInteger index = BigInteger.ZERO;
            private final BigInteger size = CharWrapper.this.size(array);

            @Override
            public boolean hasNext() {
                return (this.index.compareTo(this.size) == -1);
            }

            @Override
            public Character next() {
                char c = CharWrapper.this.getChar(array , this.index);
                this.index = this.index.add(BigInteger.ONE);
                return c;
            }
        };
    }

    /**
     * set char
     * @param array array
     * @param position position
     * @param c char value
     */
    public void setChar(ARRAY array , BigInteger position , char c);

    /**
     * size
     * @param array array
     * @return size number
     */
    public BigInteger size(ARRAY array);

}
