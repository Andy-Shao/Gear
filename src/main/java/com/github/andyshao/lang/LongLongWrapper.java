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
public class LongLongWrapper implements LongWrapper<long[]> {

    @Override
    public long getLong(long[] array , BigInteger position) {
        return array[position.intValue()];
    }

    @Override
    public void setLong(long[] array , BigInteger position , long l) {
        array[position.intValue()] = l;
    }

    @Override
    public BigInteger size(long[] array) {
        return BigInteger.valueOf(array.length);
    }

}
