package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Iterator;

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
public class LongByteWrapper implements ByteWrapper {
    private static final BigInteger FOUR = BigInteger.valueOf(4);
    private final long[] data;
    private final int end;
    private final BigInteger size;
    private final int start;

    public LongByteWrapper(long[] data) {
        this(data , 0 , data.length);
    }

    public LongByteWrapper(long[] data , int start , int end) {
        if (start >= end) throw new IllegalArgumentException("start bigger than or equals to end");
        this.data = data;
        this.start = start;
        this.end = end;
        this.size = BigInteger.valueOf((this.end - this.start) << 2);
    }

    @Override
    public byte getByte(BigInteger index) {
        if (index.compareTo(this.size) != -1) throw new IllegalArgumentException("index bigger than size");
        BigInteger[] indexs = index.divideAndRemainder(LongByteWrapper.FOUR);
        return LongOperation.getByte(this.data[indexs[0].intValue() + this.start] , indexs[1].intValue());
    }

    @Override
    public Iterator<Byte> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setByte(BigInteger index , byte b) {
        if (index.compareTo(this.size) != -1) throw new IllegalArgumentException("index bigger than size");
        BigInteger[] indexs = index.divideAndRemainder(LongByteWrapper.FOUR);
        int data_index = indexs[0].intValue() + this.start;
        this.data[data_index] = LongOperation.setByte(this.data[data_index] , indexs[1].intValue() , b);
    }

    @Override
    public BigInteger size() {
        return this.size;
    }

}
