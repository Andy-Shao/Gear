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
public class ByteIntWrapper implements IntWrapper<byte[]> {
    private static final int BASE = 2;

    @Override
    public int getInt(byte[] array , BigInteger position) {
        int result = 0x00;
        for (int j = 0 , index = position.intValue() << ByteIntWrapper.BASE ; j < 4 ; j++ , index++)
            if (index < array.length) result = IntegerOperation.setByte(result , j , array[index]);
            else if (j == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
        return result;
    }

    @Override
    public void setInt(byte[] array , BigInteger position , int i) {
        for (int j = 0 , index = position.intValue() << ByteIntWrapper.BASE ; j < 4 ; j++ , index++)
            if (index < array.length) array[index] = IntegerOperation.getByte(i , j);
            else if (j == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
    }

    @Override
    public BigInteger size(byte[] array) {
        return array.length - (array.length >> ByteIntWrapper.BASE) > 0
            ? BigInteger.valueOf((array.length >> ByteIntWrapper.BASE) + 1)
            : BigInteger.valueOf(array.length >> ByteIntWrapper.BASE);
    }

}
