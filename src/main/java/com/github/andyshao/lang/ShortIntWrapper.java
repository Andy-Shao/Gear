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
public class ShortIntWrapper implements IntWrapper<short[]> {
    private static final int BASE = 1;

    @Override
    public int getInt(short[] array , BigInteger position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setInt(short[] array , BigInteger position , int i) {
        for (int j = 0 , index = position.intValue() << BASE ; j < 2 && index < array.length ; j++ , index++) {
            if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else array[index] = IntegerOperation.getShort(i , j);
        }
    }

    @Override
    public BigInteger size(short[] array) {
        return array.length - (array.length >> BASE) > 0 ? BigInteger.valueOf((array.length >> BASE) + 1) : BigInteger
            .valueOf(array.length >> BASE);
    }
}
