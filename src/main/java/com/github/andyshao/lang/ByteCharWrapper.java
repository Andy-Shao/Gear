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
public class ByteCharWrapper implements CharWrapper<byte[]> {
    private static final int BASE = 1;

    @Override
    public char getChar(byte[] array , BigInteger position) {
        char result = 0x00;
        for (int i = 0 , index = position.intValue() << ByteCharWrapper.BASE ; i < 2 ; i++ , index++)
            if (index < array.length) result = CharOperation.setByte(result , i , array[index]);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
        return result;
    }

    @Override
    public void setChar(byte[] array , BigInteger position , char c) {
        for (int i = 0 , index = position.intValue() << ByteCharWrapper.BASE ; i < 2 ; i++ , index++)
            if (index < array.length) array[index] = CharOperation.getByte(c , i);
            else if (i == 0) throw new ArrayIndexOutOfBoundsException(index);
            else break;
    }

    @Override
    public BigInteger size(byte[] array) {
        return array.length - (array.length >> ByteCharWrapper.BASE) > 0 ? BigInteger
            .valueOf((array.length >> ByteCharWrapper.BASE) + 1) : BigInteger
            .valueOf(array.length >> ByteCharWrapper.BASE);
    }

}
