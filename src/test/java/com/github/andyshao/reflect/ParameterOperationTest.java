package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

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
}
