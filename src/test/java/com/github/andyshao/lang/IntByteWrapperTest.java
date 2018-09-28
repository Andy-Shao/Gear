package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntByteWrapperTest {

    private volatile int[] data;
    private final IntByteWrapper ibw = new IntByteWrapper();

    @BeforeEach
    public void before() {
        this.data = new int[] { 0x01020304 , 0x04030201 , 0x10203040 , 0x40302010 };
    }

    @Test
    public void getByte() {
        try {
            this.ibw.getByte(this.data , BigInteger.valueOf(-1));
            fail();
        } catch (ArrayIndexOutOfBoundsException e1) {
        }
        assertEquals(this.ibw.getByte(this.data , BigInteger.ZERO) , (byte) 0x04);
        assertEquals(this.ibw.getByte(this.data , BigInteger.ONE) , (byte) 0x03);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(2)) , (byte) 0x02);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(3)) , (byte) 0x01);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(4)) , (byte) 0x01);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(5)) , (byte) 0x02);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(6)) , (byte) 0x03);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(7)) , (byte) 0x04);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(8)) , (byte) 0x40);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(9)) , (byte) 0x30);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(10)) , (byte) 0x20);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(11)) , (byte) 0x10);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(12)) , (byte) 0x10);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(13)) , (byte) 0x20);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(14)) , (byte) 0x30);
        assertEquals(this.ibw.getByte(this.data , BigInteger.valueOf(15)) , (byte) 0x40);
        try {
            this.ibw.getByte(this.data , BigInteger.valueOf(16));
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    @Test
    public void setByte() {
        try {
            this.ibw.setByte(this.data , BigInteger.valueOf(-1) , (byte) 0x00);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        this.ibw.setByte(this.data , BigInteger.ZERO , (byte) 0x00);
        assertEquals(this.data[0] , 0x01020300);
        this.ibw.setByte(this.data , BigInteger.ZERO , (byte) 0xff);
        assertEquals(this.data[0] , 0x010203ff);
        this.ibw.setByte(this.data , BigInteger.ONE , (byte) 0xff);
        assertEquals(this.data[0] , 0x0102ffff);
        this.ibw.setByte(this.data , BigInteger.ONE , (byte) 0x00);
        assertEquals(this.data[0] , 0x010200ff);

        try {
            this.ibw.setByte(this.data , BigInteger.valueOf(16) , (byte) 0x00);
            fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
