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
public class ShortShortWrapper implements ShortWrapper<short[]> {

    @Override
    public short getShort(short[] array , BigInteger position) {
        return array[position.intValue()];
    }

    @Override
    public void setShort(short[] array , BigInteger position , short s) {
        array[position.intValue()] = s;
    }

    @Override
    public BigInteger size(short[] array) {
        return BigInteger.valueOf(array.length);
    }

}
