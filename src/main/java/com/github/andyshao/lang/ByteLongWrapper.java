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
public class ByteLongWrapper implements LongWrapper<byte[]> {
    private static final int BASE = 3;

    @Override
    public long getLong(byte[] array , BigInteger position) {
        long result = 0x00;
        for (int i = 0 , index = position.intValue() << ByteLongWrapper.BASE ; i < 8 ; i++ , index++)
            if (array.length > index) {
                if (i != 0) result = LongOperation.setByte(result , index , array[index]);
                else throw new ArrayIndexOutOfBoundsException(index);
            } else break;
        return result;
    }

    @Override
    public void setLong(byte[] array , BigInteger position , long l) {
        for (int i = 0 , index = position.intValue() << ByteLongWrapper.BASE ; i < 8 ; i++ , index++)
            if (array.length > index) {
                if (i != 0) array[index] = LongOperation.getByte(l , i);
                else throw new ArrayIndexOutOfBoundsException(index);
            } else break;
    }

    @Override
    public BigInteger size(byte[] array) {
        return array.length - (array.length >> ByteLongWrapper.BASE) > 0 ? BigInteger
            .valueOf((array.length >> ByteLongWrapper.BASE) + 1) : BigInteger
            .valueOf(array.length >> ByteLongWrapper.BASE);
    }

}
