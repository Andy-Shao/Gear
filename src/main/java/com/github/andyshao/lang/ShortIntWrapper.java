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
        int result = 0x00;
        for (int j = 0 , index = position.intValue() << ShortIntWrapper.BASE ; j < 2 ; j++ , index++)
            if (index < array.length) result = IntegerOperation.setShort(result , j , array[index]);
            else if (j == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
        return result;
    }

    @Override
    public void setInt(short[] array , BigInteger position , int i) {
        for (int j = 0 , index = position.intValue() << ShortIntWrapper.BASE ; j < 2 ; j++ , index++)
            if (index < array.length) array[index] = IntegerOperation.getShort(i , j);
            else if (j == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
    }

    @Override
    public BigInteger size(short[] array) {
        return array.length - (array.length >> ShortIntWrapper.BASE) > 0 ? BigInteger.valueOf((array.length >> ShortIntWrapper.BASE) + 1) : BigInteger.valueOf(array.length >> ShortIntWrapper.BASE);
    }
}
