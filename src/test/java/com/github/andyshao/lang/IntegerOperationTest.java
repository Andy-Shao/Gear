package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class IntegerOperationTest {

    @Test
    public void testSetByte() {
        int value = 0x01020304;

        value = IntegerOperation.setByte(value , 0 , (byte) 0x01);
        assertEquals(value , (0x01020301));

        value = IntegerOperation.setByte(value , 1 , (byte) 0x02);
        assertEquals(value , (0x01020201));

        value = IntegerOperation.setByte(value , 2 , (byte) 0x03);
        assertEquals(value , (0x01030201));

        value = IntegerOperation.setByte(value , 3 , (byte) 0x04);
        assertEquals(value , (0x04030201));
    }

    @Test
    public void testToByte() {
        byte[] bs = IntegerOperation.toByte(0x01020304);
        assertArrayEquals(bs , (new byte[] { 0x04 , 0x03 , 0x02 , 0x01 }));
    }
}
