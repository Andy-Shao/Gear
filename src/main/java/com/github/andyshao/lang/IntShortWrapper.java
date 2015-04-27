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
public class IntShortWrapper implements ShortWrapper<int[]> {
    private static final BigInteger BASE = BigInteger.valueOf(2);

    @Override
    public short getShort(int[] array , BigInteger position) {
        BigInteger indexs[] = position.divideAndRemainder(IntShortWrapper.BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        return IntegerOperation.getShort(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setShort(int[] array , BigInteger position , short s) {
        BigInteger indexs[] = position.divideAndRemainder(IntShortWrapper.BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        array[indexs[0].intValue()] = IntegerOperation.setShort(array[indexs[0].intValue()] , indexs[1].intValue() , s);
    }

    @Override
    public BigInteger size(int[] array) {
        return BigInteger.valueOf(array.length).multiply(IntShortWrapper.BASE);
    }

}
