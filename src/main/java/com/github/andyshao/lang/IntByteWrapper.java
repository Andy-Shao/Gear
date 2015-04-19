package com.github.andyshao.lang;

import java.math.BigInteger;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Apr 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class IntByteWrapper implements ByteWrapper<int[]> {

    private static final BigInteger BASE = BigInteger.valueOf(4);

    @Override
    public byte getByte(int[] array , BigInteger index) {
        BigInteger[] indexs = index.divideAndRemainder(IntByteWrapper.BASE);
        try {
            return IntegerOperation.getByte(array[indexs[0].intValue()] , indexs[1].intValue());
        } catch (IllegalArgumentException e) {
            throw new ArrayIndexOutOfBoundsException(indexs[0].intValue());
        }
    }

    @Override
    public void setByte(int[] array , BigInteger index , byte b) {
        BigInteger[] indexs = index.divideAndRemainder(IntByteWrapper.BASE);
        try {
            array[indexs[0].intValue()] =
                IntegerOperation.setByte(array[indexs[0].intValue()] , indexs[1].intValue() , b);
        } catch (IllegalArgumentException e) {
            throw new ArrayIndexOutOfBoundsException(indexs[0].intValue());
        }
    }

    @Override
    public BigInteger size(int[] array) {
        BigInteger length = BigInteger.valueOf(array.length);
        return length.multiply(IntByteWrapper.BASE);
    }
}
