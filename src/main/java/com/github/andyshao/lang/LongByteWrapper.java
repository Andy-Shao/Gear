package com.github.andyshao.lang;

import java.math.BigInteger;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 3, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class LongByteWrapper implements ByteWrapper<long[]> {
    private static final BigInteger BASE = BigInteger.valueOf(8);

    @Override
    public byte getByte(long[] array , BigInteger index) {
        BigInteger indexs[] = index.divideAndRemainder(LongByteWrapper.BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(index.toString());
        return LongOperation.getByte(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setByte(long[] array , BigInteger index , byte b) {
        BigInteger indexs[] = index.divideAndRemainder(LongByteWrapper.BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(index.toString());
        array[indexs[0].intValue()] = LongOperation.setByte(array[indexs[0].intValue()] , indexs[1].intValue() , b);
    }

    @Override
    public BigInteger size(long[] array) {
        return BigInteger.valueOf(array.length).multiply(LongByteWrapper.BASE);
    }

}
