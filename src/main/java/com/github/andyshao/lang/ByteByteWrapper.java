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
public class ByteByteWrapper implements ByteWrapper<byte[]> {

    @Override
    public byte getByte(byte[] array , BigInteger index) {
        return array[index.intValue()];
    }

    @Override
    public void setByte(byte[] array , BigInteger index , byte b) {
        array[index.intValue()] = b;
    }

    @Override
    public BigInteger size(byte[] array) {
        return BigInteger.valueOf(array.length);
    }

}
