package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.andyshao.reflect.ArrayOperation;

public class ConvertTest {

    @Test
    public void testBoolean() {
        String trueStr = "true";
        String falseStr = "false";

        assertTrue(Convert.OB_2_BOOLEAN.convert(trueStr));
        assertFalse(Convert.OB_2_BOOLEAN.convert(falseStr));
    }

    @Test
    public void testByteArray() {
        String hexString = "416e64792d5368616f";
        byte[] bs = "Andy-Shao".getBytes();

        {
            String hexStr = Convert.BYTES_2_HEX.convert(ArrayOperation.pack_unpack(bs , Byte[].class));
            assertTrue(hexStr.equalsIgnoreCase(hexString));
        }

        {
            byte[] bs2 = ArrayOperation.pack_unpack(Convert.HEX_2_BYTES.convert(hexString) , byte[].class);
            assertArrayEquals(bs , bs2);
        }
    }

    @Test
    public void testConverting() {
        Object in = new BigDecimal("12.34");
        String out = Convert.<Object , String> converting(in , (Object input) -> {
            return input.toString();
        });
        assertEquals(out , "12.34");
    }

    @Test
    public void testDouble() {
        double d = 1.23456;
        String dStr = "1.23456";
        BigDecimal bigDecimal = new BigDecimal(dStr);

        double myDouble = Convert.OB_2_DOUBLE.convert(bigDecimal);
        assertEquals(myDouble , d);

        myDouble = Convert.OB_2_DOUBLE.convert(dStr);
        assertEquals(myDouble , d);
    }

    @Test
    public void testFloat() {
        float f = 1.23F;
        String fStr = "1.23";
        BigDecimal bigDecimal = new BigDecimal(fStr);

        float myFloat = Convert.OB_2_FLOAT.convert(fStr);
        assertEquals(myFloat , f);

        myFloat = Convert.OB_2_FLOAT.convert(bigDecimal);
        assertEquals(myFloat , f);
        
        assertEquals(Convert.OB_2_FLOAT.convert(new Integer(25)) , (Float)25f);
    }

    @Test
    public void testInt() {
        int i = 15_000;
        String iStr = "15000";
        BigInteger bigInteger = new BigInteger(iStr);
        BigDecimal bigDecimal = new BigDecimal(iStr);

        int myInt = Convert.OB_2_INT.convert(iStr);
        assertEquals(myInt , i);

        myInt = Convert.OB_2_INT.convert(bigDecimal);
        assertEquals(myInt , i);

        myInt = Convert.OB_2_INT.convert(bigInteger);
        assertEquals(myInt , i);
    }

    @Test
    public void testLong() {
        long l = 15_000_000L;
        String lStr = "15000000";
        BigInteger bigInteger = new BigInteger(lStr);
        BigDecimal bigDecimal = new BigDecimal(lStr);

        long myLong = Convert.OB_2_LONG.convert(lStr);
        assertEquals(myLong , l);

        myLong = Convert.OB_2_LONG.convert(bigDecimal);
        assertEquals(myLong , l);

        myLong = Convert.OB_2_LONG.convert(bigInteger);
        assertEquals(myLong , l);
    }

    @Test
    public void testShort() {
        short s = 150;
        String sStr = "150";
        BigInteger bigInteger = new BigInteger(sStr);
        BigDecimal bigDecimal = new BigDecimal(sStr);

        short myShort = Convert.OB_2_SHORT.convert(sStr);
        assertEquals(myShort , s);

        myShort = Convert.OB_2_SHORT.convert(bigInteger);
        assertEquals(myShort , s);

        myShort = Convert.OB_2_SHORT.convert(bigDecimal);
        assertEquals(myShort , s);
    }

    @Test
    public void testString() {
        List<Character> list = Arrays.asList('a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g');
        String str = "[a, b, c, d, e, f, g]";

        assertEquals(str , Convert.OB_2_STR.convert(list));
    }
}
