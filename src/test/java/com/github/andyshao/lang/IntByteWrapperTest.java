package com.github.andyshao.lang;

import java.math.BigInteger;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntByteWrapperTest {

    private volatile int[] data;
    private final IntByteWrapper ibw = new IntByteWrapper();

    @Before
    public void before() {
        this.data = new int[] {
            0x01020304 , 0x04030201 , 0x10203040 , 0x40302010
        };
    }

    @Test
    public void getByte() {
        try {
            this.ibw.getByte(this.data , BigInteger.valueOf(-1));
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException e1) {
        }
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.ZERO) , Matchers.is((byte) 0x04));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.ONE) , Matchers.is((byte) 0x03));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(2)) , Matchers.is((byte) 0x02));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(3)) , Matchers.is((byte) 0x01));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(4)) , Matchers.is((byte) 0x01));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(5)) , Matchers.is((byte) 0x02));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(6)) , Matchers.is((byte) 0x03));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(7)) , Matchers.is((byte) 0x04));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(8)) , Matchers.is((byte) 0x40));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(9)) , Matchers.is((byte) 0x30));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(10)) , Matchers.is((byte) 0x20));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(11)) , Matchers.is((byte) 0x10));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(12)) , Matchers.is((byte) 0x10));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(13)) , Matchers.is((byte) 0x20));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(14)) , Matchers.is((byte) 0x30));
        Assert.assertThat(this.ibw.getByte(this.data , BigInteger.valueOf(15)) , Matchers.is((byte) 0x40));
        try {
            this.ibw.getByte(this.data , BigInteger.valueOf(16));
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
    
    @Test
    public void setByte(){
        try {
            this.ibw.setByte(data , BigInteger.valueOf(-1) , (byte) 0x00);
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        this.ibw.setByte(this.data , BigInteger.ZERO , (byte) 0x00);
        Assert.assertThat(this.data[0] , Matchers.is(0x01020300));
        this.ibw.setByte(this.data , BigInteger.ZERO , (byte) 0xff);
        Assert.assertThat(this.data[0] , Matchers.is(0x010203ff));
        this.ibw.setByte(this.data , BigInteger.ONE, (byte) 0xff);
        Assert.assertThat(this.data[0] , Matchers.is(0x0102ffff));
        this.ibw.setByte(this.data , BigInteger.ONE, (byte) 0x00);
        Assert.assertThat(this.data[0] , Matchers.is(0x010200ff));
        
        try {
            this.ibw.setByte(data , BigInteger.valueOf(16) , (byte) 0x00);
            Assert.fail();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
