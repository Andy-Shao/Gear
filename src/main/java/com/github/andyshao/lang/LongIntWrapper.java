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
public class LongIntWrapper implements IntWrapper<long[]> {
    private static final BigInteger BASE = BigInteger.valueOf(2);

    @Override
    public int getInt(long[] array , BigInteger position) {
        BigInteger indexs[] = position.divideAndRemainder(LongIntWrapper.BASE);
        if (indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        return LongOperation.getInt(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setInt(long[] array , BigInteger position , int i) {
        BigInteger indexs[] = position.divideAndRemainder(LongIntWrapper.BASE);
        if (indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        array[indexs[0].intValue()] = LongOperation.setInt(array[indexs[0].intValue()] , indexs[1].intValue() , i);
    }

    @Override
    public BigInteger size(long[] array) {
        return BigInteger.valueOf(array.length).multiply(LongIntWrapper.BASE);
    }

}
