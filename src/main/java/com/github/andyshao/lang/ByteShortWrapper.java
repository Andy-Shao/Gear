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
public class ByteShortWrapper implements ShortWrapper<byte[]> {
    private static final int BASE = 1;

    @Override
    public short getShort(byte[] array , BigInteger position) {
        short result = 0x00;
        for (int i = 0 , index = position.intValue() << ByteShortWrapper.BASE ; i < 2 ; i++ , index++)
            if (index < array.length) result = ShortOperation.setByte(result , i , array[index]);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
        return result;
    }

    @Override
    public void setShort(byte[] array , BigInteger position , short s) {
        for (int i = 0 , index = position.intValue() << ByteShortWrapper.BASE ; i < 2 ; i++ , index++)
            if (index < array.length) array[index] = ShortOperation.getByte(s , i);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
    }

    @Override
    public BigInteger size(byte[] array) {
        return array.length - (array.length >> ByteShortWrapper.BASE) > 0 ? BigInteger.valueOf((array.length >> ByteShortWrapper.BASE) + 1) : BigInteger.valueOf(array.length >> ByteShortWrapper.BASE);
    }
}
