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
public class IntCharWrapper implements CharWrapper<int[]> {
    private static final BigInteger BASE = BigInteger.valueOf(2);

    @Override
    public char getChar(int[] array , BigInteger position) {
        BigInteger indexs[] = position.divideAndRemainder(IntCharWrapper.BASE);
        if (indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        return (char) IntegerOperation.getShort(array[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public void setChar(int[] array , BigInteger position , char c) {
        BigInteger indexs[] = position.divideAndRemainder(IntCharWrapper.BASE);
        if (indexs[1].intValue() < 0) throw new ArrayIndexOutOfBoundsException(position.toString());
        array[indexs[0].intValue()] = IntegerOperation.setShort(array[indexs[0].intValue()] , indexs[1].intValue() , (short) c);
    }

    @Override
    public BigInteger size(int[] array) {
        return BigInteger.valueOf(array.length).multiply(IntCharWrapper.BASE);
    }

}
