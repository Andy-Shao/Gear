package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ClassTest {

    @Test
    public void testisPrimitive() {
        assertEquals(int.class.isPrimitive() , true);
        assertEquals(boolean.class.isPrimitive() , true);
        assertEquals(short.class.isPrimitive() , true);
        assertEquals(char.class.isPrimitive() , true);
        assertEquals(float.class.isPrimitive() , true);
        assertEquals(double.class.isPrimitive() , true);
        assertEquals(long.class.isPrimitive() , true);
        assertEquals(byte.class.isPrimitive() , true);
        assertEquals(void.class.isPrimitive() , true);
        assertEquals(Integer.class.isPrimitive() , false);
        assertEquals(Boolean.class.isPrimitive() , false);
        assertEquals(Short.class.isPrimitive() , false);
        assertEquals(Character.class.isPrimitive() , false);
        assertEquals(Float.class.isPrimitive() , false);
        assertEquals(Double.class.isPrimitive() , false);
        assertEquals(Long.class.isPrimitive() , false);
        assertEquals(Byte.class.isPrimitive() , false);
        assertEquals(Void.class.isPrimitive() , false);
    }
}
