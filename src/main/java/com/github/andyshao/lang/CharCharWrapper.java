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
public class CharCharWrapper implements CharWrapper<char[]> {

    @Override
    public char getChar(char[] array , BigInteger position) {
        return array[position.intValue()];
    }

    @Override
    public void setChar(char[] array , BigInteger position , char c) {
        array[position.intValue()] = c;
    }

    @Override
    public BigInteger size(char[] array) {
        return BigInteger.valueOf(array.length);
    }
}
