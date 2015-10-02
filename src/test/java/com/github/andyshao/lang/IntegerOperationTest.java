package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class IntegerOperationTest {

    public void testSetByte() {
        int value = 0x01020304;

        value = IntegerOperation.setByte(value , 0 , (byte) 0x01);
        Assert.assertThat(value , Matchers.is(0x01020301));

        value = IntegerOperation.setByte(value , 1 , (byte) 0x02);
        Assert.assertThat(value , Matchers.is(0x01020201));

        value = IntegerOperation.setByte(value , 2 , (byte) 0x03);
        Assert.assertThat(value , Matchers.is(0x01030201));

        value = IntegerOperation.setByte(value , 3 , (byte) 0x04);
        Assert.assertThat(value , Matchers.is(0x04030201));
    }

    @Test
    public void testToByte() {
        byte[] bs = IntegerOperation.toByte(0x01020304);
        Assert.assertThat(bs , Matchers.is(new byte[] { 0x04 , 0x03 , 0x02 , 0x01 }));
    }
}
