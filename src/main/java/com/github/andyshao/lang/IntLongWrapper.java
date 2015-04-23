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
public class IntLongWrapper implements LongWrapper<int[]> {
    private static final int BASE = 1;

    @Override
    public long getLong(int[] array , BigInteger position) {
        long result = 0x00;
        for (int i = 0 , index = position.intValue() << IntLongWrapper.BASE ; i < 2 ; i++ , index++)
            if (array.length > index) {
                if (i != 0) result = LongOperation.setInt(result , i , array[index]);
                else throw new ArrayIndexOutOfBoundsException(index);
            } else break;
        return result;
    }

    @Override
    public void setLong(int[] array , BigInteger position , long l) {
        for (int i = 0 , index = position.intValue() << IntLongWrapper.BASE ; i < 2 ; i++ , index++)
            if (array.length > index) {
                if (i != 0) array[index] = LongOperation.getInt(l , i);
                else throw new ArrayIndexOutOfBoundsException(index);
            } else break;
    }

    @Override
    public BigInteger size(int[] array) {
        return array.length - (array.length >> IntLongWrapper.BASE) > 0 ? BigInteger
            .valueOf((array.length >> IntLongWrapper.BASE) + 1) : BigInteger
            .valueOf(array.length >> IntLongWrapper.BASE);
    }

}
