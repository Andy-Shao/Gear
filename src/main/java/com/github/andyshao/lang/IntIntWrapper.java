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
public class IntIntWrapper implements IntWrapper<int[]> {

    @Override
    public int getInt(int[] array , BigInteger position) {
        return array[position.intValue()];
    }
    

    @Override
    public void setInt(int[] array , BigInteger position , int i) {
        array[position.intValue()] = i;
    }

    @Override
    public BigInteger size(int[] array) {
        return BigInteger.valueOf(array.length);
    }

}
