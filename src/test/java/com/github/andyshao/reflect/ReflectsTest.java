package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class ReflectsTest {

    class MyClass {
        void myMethod(String myStr , String myStr2) {
        }
    }

    @Test
    public void getParameterNameTest() throws IOException {
        String[] names = Reflects.getMethodParamNames(Reflects.getDeclaredMethod(MyClass.class , "myMethod" , String.class , String.class));
        assertArrayEquals(names , (new String[] { "myStr" , "myStr2" }));
    }
}
