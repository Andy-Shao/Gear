package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.util.ArrayTools;

public class ByteOperationTest {
    private volatile byte b;
    private volatile byte[] bs;

    @Before
    public void before() {
        this.b = (byte) 0xa5;
        this.bs = ArrayTools.pack_unpack(new int[] {
            0x00 , 0x01 , 0x02 , 0x04 , 0x08 , 0x10 , 0x20 , 0x40 , 0x80 , 0xff , 0xa5
        } , byte[].class , (input) -> {
            return (byte) ((int) input);
        });
    }

    @Test
    public void teatToString() {
        Assert.assertThat(ByteOperation.toString(this.b) , Matchers.is("10100101"));
        Assert.assertThat(ByteOperation.toString(this.bs) , Matchers.is("00000000,00000001,00000010,00000100,00001000,"
            + "00010000,00100000,01000000,10000000,11111111,10100101"));
    }

    @Test
    public void testGet() {
        int position = 8;
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.bs) , Matchers.is(0));

        position = 0;
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(0));
        Assert.assertThat(ByteOperation.bitGet(position++ , this.b) , Matchers.is(1));
    }

    @Test
    public void testSet() {
        int position = 0;
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10101101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10110101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B11100101));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.b)[0] , Matchers.is((byte) 0B10100101));

        position = 0;
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00000001));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00000011));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00000111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00001111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00011111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B00111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B01111111));
        Assert.assertThat(ByteOperation.bitSet(position++ , 1 , this.bs)[0] , Matchers.is((byte) 0B11111111));
    }
}
