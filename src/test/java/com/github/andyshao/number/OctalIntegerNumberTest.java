package com.github.andyshao.number;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.andyshao.lang.number.OctalIntegerNumber;

public class OctalIntegerNumberTest {
    @Test
    public void testInstanceStr() {
        OctalIntegerNumber octalIntegerNumber = new OctalIntegerNumber();
        octalIntegerNumber.instance("10");
        Assertions.assertEquals(octalIntegerNumber.convert().intValue() , 8);
        
        octalIntegerNumber.instance("100");
        Assertions.assertEquals(octalIntegerNumber.convert().intValue() , 64);
        
        octalIntegerNumber.instance("0");
        Assertions.assertEquals(octalIntegerNumber.convert().intValue() , 0);
        
        octalIntegerNumber.instance("1");
        Assertions.assertEquals(octalIntegerNumber.convert().intValue() , 1);
    }
}
