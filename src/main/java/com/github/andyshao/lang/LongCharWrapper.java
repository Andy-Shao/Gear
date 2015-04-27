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
public class LongCharWrapper implements CharWrapper<long[]> {
    private static final BigInteger BASE = BigInteger.valueOf(4);

    @Override
    public char getChar(long[] array , BigInteger position) {
        BigInteger indexs[] = position.divideAndRemainder(LongCharWrapper.BASE);
        if (indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        return (char) LongOperation.getByte(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setChar(long[] array , BigInteger position , char c) {
        BigInteger indexs[] = position.divideAndRemainder(LongCharWrapper.BASE);
        if (indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        array[indexs[0].intValue()] =
            LongOperation.setShort(array[indexs[0].intValue()] , indexs[1].intValue() , (short) c);
    }

    @Override
    public BigInteger size(long[] array) {
        return BigInteger.valueOf(array.length).multiply(LongCharWrapper.BASE);
    }

}
