package com.github.andyshao.reflect;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class ReflectsTest {

    class MyClass {
        void myMethod(String myStr , String myStr2) {
        }
    }

    @Test
    public void getParameterNameTest() throws IOException {
        String[] names = Reflects
            .getMethodParamNames(Reflects.getDeclaredMethod(MyClass.class , "myMethod" , String.class , String.class));
        Assert.assertThat(names , Matchers.is(new String[] { "myStr" , "myStr2" }));
    }
}
