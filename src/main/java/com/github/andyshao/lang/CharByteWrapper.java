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
        BigInteger indexs[] = index.divideAndRemainder(CharByteWrapper.BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(index.toString());
        return CharOperation.getByte(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setByte(char[] array , BigInteger index , byte b) {
        BigInteger indexs[] = index.divideAndRemainder(CharByteWrapper.BASE);
        if(indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(index.toString());
        array[indexs[0].intValue()] = CharOperation.setByte(array[indexs[0].intValue()] , indexs[1].intValue() , b);
    }

    @Override
    public BigInteger size(char[] array) {
        return BigInteger.valueOf(array.length).multiply(CharByteWrapper.BASE);
    }

}
