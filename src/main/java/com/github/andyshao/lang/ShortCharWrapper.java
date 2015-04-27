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
public class ShortCharWrapper implements CharWrapper<short[]> {

    @Override
    public char getChar(short[] array , BigInteger position) {
        return (char) array[position.intValue()];
    }

    @Override
    public void setChar(short[] array , BigInteger position , char c) {
        array[position.intValue()] = (short) c;
    }

    @Override
    public BigInteger size(short[] array) {
        return BigInteger.valueOf(array.length);
    }
}
