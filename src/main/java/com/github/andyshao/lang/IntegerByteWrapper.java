package com.github.andyshao.lang;

import java.math.BigInteger;
import java.util.Iterator;

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
public class IntegerByteWrapper implements ByteWrapper {
    private class MyIterator implements Iterator<Byte> {
        private BigInteger index = BigInteger.ZERO;

        @Override
        public boolean hasNext() {
            return this.index.compareTo(IntegerByteWrapper.this.size) == -1;
        }

        @Override
        public Byte next() {
            Byte b = IntegerByteWrapper.this.getByte(this.index);
            this.index.add(BigInteger.ONE);
            return b;
        }

    }

    private static final BigInteger TWO = BigInteger.valueOf(2);
    private final int[] data;
    private final int end;
    private final BigInteger size;
    private final int start;

    public IntegerByteWrapper(int[] data) {
        this(data , 0 , data.length);
    }

    public IntegerByteWrapper(int[] data , int start , int end) {
        if (start >= end) throw new IllegalArgumentException();
        this.data = data;
        this.start = start;
        this.end = end;
        this.size = BigInteger.valueOf((this.end - this.start) << 1);
    }

    @Override
    public byte getByte(BigInteger index) {
        if (index.compareTo(this.size) != -1) throw new IllegalArgumentException("index bigger than size");
        BigInteger[] indexs = index.divideAndRemainder(IntegerByteWrapper.TWO);
        return IntegerOperation.getByte(this.data[indexs[0].intValue() + this.start] , indexs[1].intValue());
    }

    @Override
    public Iterator<Byte> iterator() {
        return this.new MyIterator();
    }

    @Override
    public void setByte(BigInteger index , byte b) {
        if (index.compareTo(this.size) != -1) throw new IllegalArgumentException("index bigger than size");
        BigInteger[] indexs = index.divideAndRemainder(IntegerByteWrapper.TWO);
        int date_index = indexs[0].intValue() + this.start;
        this.data[date_index] = IntegerOperation.setByte(this.data[date_index] , indexs[1].intValue() , b);
    }

    @Override
    public BigInteger size() {
        return this.size;
    }

}
