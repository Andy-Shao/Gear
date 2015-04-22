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
public class CharByteWrapper implements ByteWrapper<char[]> {
    private static final BigInteger BASE = BigInteger.valueOf(2);

    @Override
    public byte getByte(char[] array , BigInteger index) {
        BigInteger indexs[] = index.divideAndRemainder(BASE);
        try {
            return CharOperation.getByte(array[indexs[0].intValue()] , indexs[1].intValue());
        } catch (IllegalArgumentException e) {
            throw new ArrayIndexOutOfBoundsException(indexs[0].intValue());
        }
    }

    @Override
    public void setByte(char[] array , BigInteger index , byte b) {
        BigInteger indexs[] = index.divideAndRemainder(BASE);
        try {
            array[indexs[0].intValue()] = CharOperation.setByte(array[indexs[0].intValue()] , indexs[1].intValue() , b);
        } catch (IllegalArgumentException e) {
            throw new ArrayIndexOutOfBoundsException(indexs[0].intValue());
        }
    }

    @Override
    public BigInteger size(char[] array) {
        return BigInteger.valueOf(array.length).multiply(BASE);
    }

}
