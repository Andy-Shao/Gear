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

    private BigInteger size;

    public IntegerByteWrapper(int[] data) {
        this.data = data;
        this.size = BigInteger.valueOf(this.data.length).multiply(IntegerByteWrapper.TWO);
    }

    @Override
    public byte getByte(BigInteger index) {
        BigInteger[] indexs = index.divideAndRemainder(IntegerByteWrapper.TWO);
        return IntegerOperation.getByte(this.data[indexs[0].intValue()] , indexs[1].intValue());
    }

    @Override
    public Iterator<Byte> iterator() {
        return this.new MyIterator();
    }

    @Override
    public void setByte(BigInteger index , byte b) {
        BigInteger[] indexs = index.divideAndRemainder(IntegerByteWrapper.TWO);
        this.data[indexs[0].intValue()] =
            IntegerOperation.setByte(this.data[indexs[0].intValue()] , indexs[1].intValue() , b);
    }

    @Override
    public BigInteger size() {
        return this.size;
    }

}
