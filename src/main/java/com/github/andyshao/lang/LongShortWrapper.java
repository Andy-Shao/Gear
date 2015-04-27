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
public class LongShortWrapper implements ShortWrapper<long[]> {
    private static final BigInteger BASE = BigInteger.valueOf(4);

    @Override
    public short getShort(long[] array , BigInteger position) {
        BigInteger indexs[] = position.divideAndRemainder(BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        return LongOperation.getShort(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setShort(long[] array , BigInteger position , short s) {
        BigInteger indexs[] = position.divideAndRemainder(BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        array[indexs[0].intValue()] = LongOperation.setShort(array[indexs[0].intValue()] , indexs[1].intValue() , s);
    }

    @Override
    public BigInteger size(long[] array) {
        return BigInteger.valueOf(array.length).multiply(BASE);
    }

}
