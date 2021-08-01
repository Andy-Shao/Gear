package com.github.andyshao.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterOperationTest {
    static class MyClass {
        public void myMethod(String argOne) {
        }
    }

    static class MyClass2 implements MyInterface {
        @Override
        public void myMethod(String argTwo) {
        }
    }

    interface MyInterface {
        public void myMethod(String argTwo);
    }

    @Test
    public void testGetParameterNames() {
        String[] ps = ParameterOperation.getMethodParamNames(MethodOperation.getMethod(MyClass.class , "myMethod" , String.class));
        assertArrayEquals(ps , (new String[] { "argOne" }));

        try {
            ps = ParameterOperation.getMethodParamNames(MethodOperation.getMethod(MyInterface.class , "myMethod" , String.class));
            fail();
        } catch (ReflectiveOperationException e) {
        }

        ps = ParameterOperation.getMethodParamNames(MethodOperation.getMethod(MyClass2.class , "myMethod" , String.class));
        assertArrayEquals(ps , (new String[] { "argTwo" }));
    }

    @Test
    public void getGenericParameterType() {
        final Method testMethod = MethodOperation.getMethod(MethodOperationTest.Data.class, "testMethod", List.class, int.class, String.class, Map.class);
        final List<GenericNode> genericParameterType = ParameterOperation.getParameterTypesInfoByNative(testMethod);
        assertNotNull(genericParameterType);
        GenericNode node = genericParameterType.get(0);
        assertTrue(node.isGeneiric());
        assertEquals(node.getDeclareType(), List.class);
        assertEquals(node.getComponentTypes().size(), 1);
        assertTrue(node.getComponentTypes().get(0).isGeneiric());
        assertEquals(node.getComponentTypes().get(0).getDeclareType(), List.class);
        assertEquals(node.getComponentTypes().get(0).getComponentTypes().size(), 1);
        assertEquals(node.getComponentTypes().get(0).getComponentTypes().get(0).getDeclareType(), Integer.class);
        node = genericParameterType.get(1);
        assertFalse(node.isGeneiric());
        assertEquals(node.getDeclareType(), int.class);
        node = genericParameterType.get(2);
        assertFalse(node.isGeneiric());
        assertEquals(node.getDeclareType(), String.class);
        node = genericParameterType.get(3);
        assertTrue(node.isGeneiric());
        assertEquals(node.getDeclareType(), Map.class);
        assertEquals(node.getComponentTypes().size(), 2);
        assertFalse(node.getComponentTypes().get(0).isGeneiric());
        assertEquals(node.getComponentTypes().get(0).getDeclareType(), String.class);
        assertTrue(node.getComponentTypes().get(1).isGeneiric());
        assertEquals(node.getComponentTypes().get(1).getDeclareType(), List.class);
    }
}
