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
public class ShortLongWrapper implements LongWrapper<short[]> {
    private static final int BASE = 2;

    @Override
    public long getLong(short[] array , BigInteger position) {
        long result = 0x00L;
        for (int i = 0 , index = position.intValue() << ShortLongWrapper.BASE ; i < 4 ; i++ , index++)
            if (index < array.length) result = LongOperation.setShort(result , i , array[index]);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
        return result;
    }

    @Override
    public void setLong(short[] array , BigInteger position , long l) {
        for (int i = 0 , index = position.intValue() << ShortLongWrapper.BASE ; i < 4 ; i++ , index++)
            if (index < array.length) array[index] = LongOperation.getShort(l , i);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
    }

    @Override
    public BigInteger size(short[] array) {
        return array.length - (array.length >> ShortLongWrapper.BASE) > 0 ? BigInteger.valueOf((array.length >> ShortLongWrapper.BASE) + 1) : BigInteger.valueOf(array.length >> ShortLongWrapper.BASE);
    }
}
