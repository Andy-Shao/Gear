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
public class ShortByteWrapper implements ByteWrapper<short[]> {
    private static final BigInteger BASE = BigInteger.valueOf(2);

    @Override
    public byte getByte(short[] array , BigInteger index) {
        BigInteger indexs[] = index.divideAndRemainder(ShortByteWrapper.BASE);
        try {
            return ShortOperation.getByte(array[indexs[0].intValue()] , indexs[1].intValue());
        } catch (IllegalArgumentException e) {
            throw new ArrayIndexOutOfBoundsException(indexs[0].intValue());
        }
    }

    @Override
    public void setByte(short[] array , BigInteger index , byte b) {
        BigInteger indexs[] = index.divideAndRemainder(ShortByteWrapper.BASE);
        try {
            array[indexs[0].intValue()] =
                ShortOperation.setByte(array[indexs[0].intValue()] , indexs[1].intValue() , b);
        } catch (IllegalArgumentException e) {
            throw new ArrayIndexOutOfBoundsException(indexs[0].intValue());
        }
    }

    @Override
    public BigInteger size(short[] array) {
        return BigInteger.valueOf(array.length).multiply(ShortByteWrapper.BASE);
    }
}
