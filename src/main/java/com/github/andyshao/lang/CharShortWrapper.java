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
 */
public class CharShortWrapper implements ShortWrapper<char[]> {

    @Override
    public short getShort(char[] array , BigInteger position) {
        return (short) array[position.intValue()];
    }

    @Override
    public void setShort(char[] array , BigInteger position , short s) {
        array[position.intValue()] = (char) s;
    }

    @Override
    public BigInteger size(char[] array) {
        return BigInteger.valueOf(array.length);
    }

}
