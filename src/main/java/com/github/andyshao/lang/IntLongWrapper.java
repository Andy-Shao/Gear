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
public class IntLongWrapper implements LongWrapper<int[]> {
    private static final BigInteger BASE = BigInteger.valueOf(2);

    @Override
    public long getLong(int[] array , BigInteger position) {
        long l = 0x00;
        l = LongOperation.setInt(l , 0 , array[position.intValue()]);
        l = LongOperation.setInt(l , 1 , array[position.intValue() + 1]);
        return l;
    }

    @Override
    public void setLong(int[] array , BigInteger position , long l) {
        array[position.intValue()] = LongOperation.getInt(l , 0);
        array[position.intValue() + 1] = LongOperation.getInt(l , 1);
    }

    @Override
    public BigInteger size(int[] array) {
        BigInteger indexs[] = BigInteger.valueOf(array.length).divideAndRemainder(IntLongWrapper.BASE);
        return indexs[1].intValue() > 0 ? indexs[0].add(BigInteger.ONE) : indexs[0];
    }

}
