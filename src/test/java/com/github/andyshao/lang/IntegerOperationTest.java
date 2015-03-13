package com.github.andyshao.lang;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class IntegerOperationTest {

    @Test
    public void testToByte() {
        byte[] bs = IntegerOperation.toByte(0x01020304);
        Assert.assertThat(bs , Matchers.is(new byte[] {
            0x04 , 0x03 , 0x02 , 0x01
        }));
    }
}
