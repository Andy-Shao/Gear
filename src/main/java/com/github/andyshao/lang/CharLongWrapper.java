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
public class CharLongWrapper implements LongWrapper<char[]> {
    private static final int BASE = 2;

    @Override
    public long getLong(char[] array , BigInteger position) {
        long result = 0x00L;
        for (int i = 0 , index = position.intValue() << CharLongWrapper.BASE ; i < 4 ; i++ , index++)
            if (index < array.length) result = LongOperation.setShort(result , i , (short) array[index]);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
        return result;
    }

    @Override
    public void setLong(char[] array , BigInteger position , long l) {
        for (int i = 0 , index = position.intValue() << CharLongWrapper.BASE ; i < 4 ; i++ , index++)
            if (index < array.length) array[index] = (char) LongOperation.getShort(l , i);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
    }

    @Override
    public BigInteger size(char[] array) {
        return array.length - (array.length >> CharLongWrapper.BASE) > 0
            ? BigInteger.valueOf((array.length >> CharLongWrapper.BASE) + 1)
            : BigInteger.valueOf(array.length >> CharLongWrapper.BASE);
    }
}
