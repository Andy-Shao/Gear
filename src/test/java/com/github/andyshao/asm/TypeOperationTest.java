package com.github.andyshao.asm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;

public class TypeOperationTest {

    @Test
    public void testGetArgumentClasses() {
        assertArrayEquals(TypeOperation.getArgumentClasses("(I)V") , new Class<?>[] { int.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(Z)V") , new Class<?>[] { boolean.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(C)V") , new Class<?>[] { char.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(B)V") , new Class<?>[] { byte.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(S)V") , new Class<?>[] { short.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(F)V") , new Class<?>[] { float.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(J)V") , new Class<?>[] { long.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(D)V") , new Class<?>[] { double.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(Ljava/lang/String;)V") , new Class<?>[] { String.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(Ljava/lang/Void;)V") , new Class<?>[] { Void.class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([I)V") , new Class<?>[] { int[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([Z)V") , new Class<?>[] { boolean[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([C)V") , new Class<?>[] { char[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([B)V") , new Class<?>[] { byte[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([S)V") , new Class<?>[] { short[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([F)V") , new Class<?>[] { float[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([J)V") , new Class<?>[] { long[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([D)V") , new Class<?>[] { double[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("([Ljava/lang/String;)V") , new Class<?>[] { String[].class });
        assertArrayEquals(TypeOperation.getArgumentClasses("(II)V") , new Class<?>[] { int.class , int.class });
    }

    @Test
    public void testGetReturnClass() {
        assertTrue(void.class.isAssignableFrom(TypeOperation.getReturnClass("()V")));
        assertTrue(int.class.isAssignableFrom(TypeOperation.getReturnClass("()I")));
        assertTrue(boolean.class.isAssignableFrom(TypeOperation.getReturnClass("()Z")));
        assertTrue(char.class.isAssignableFrom(TypeOperation.getReturnClass("()C")));
        assertTrue(byte.class.isAssignableFrom(TypeOperation.getReturnClass("()B")));
        assertTrue(short.class.isAssignableFrom(TypeOperation.getReturnClass("()S")));
        assertTrue(float.class.isAssignableFrom(TypeOperation.getReturnClass("()F")));
        assertTrue(long.class.isAssignableFrom(TypeOperation.getReturnClass("()J")));
        assertTrue(double.class.isAssignableFrom(TypeOperation.getReturnClass("()D")));
        assertTrue(String.class.isAssignableFrom(TypeOperation.getReturnClass("()Ljava/lang/String;")));
        assertTrue(Void.class.isAssignableFrom(TypeOperation.getReturnClass("()Ljava/lang/Void;")));
        assertTrue(int[].class.isAssignableFrom(TypeOperation.getReturnClass("()[I")));
        assertTrue(boolean[].class.isAssignableFrom(TypeOperation.getReturnClass("()[Z")));
        assertTrue(char[].class.isAssignableFrom(TypeOperation.getReturnClass("()[C")));
        assertTrue(byte[].class.isAssignableFrom(TypeOperation.getReturnClass("()[B")));
        assertTrue(short[].class.isAssignableFrom(TypeOperation.getReturnClass("()[S")));
        assertTrue(float[].class.isAssignableFrom(TypeOperation.getReturnClass("()[F")));
        assertTrue(long[].class.isAssignableFrom(TypeOperation.getReturnClass("()[J")));
        assertTrue(double[].class.isAssignableFrom(TypeOperation.getReturnClass("()[D")));
        assertTrue(String[].class.isAssignableFrom(TypeOperation.getReturnClass("()[Ljava/lang/String;")));
        assertTrue(Void[].class.isAssignableFrom(TypeOperation.getReturnClass("()[Ljava/lang/Void;")));
    }

    @Test
    public void testGetType() {
        Type type = Type.getType("Ljava/lang/Object;");
        assertTrue(TypeOperation.getClass(type) == Object.class);
        type = Type.getType("Ljava/util/List;");
        assertTrue(TypeOperation.getClass(type) == List.class);
        type = Type.getType("Ljava/util/Map;");
        assertTrue(TypeOperation.getClass(type) == Map.class);
        type = Type.getType("Ljava/util/function/BiFunction;");
        assertTrue(TypeOperation.getClass(type) == BiFunction.class);
    }
}
