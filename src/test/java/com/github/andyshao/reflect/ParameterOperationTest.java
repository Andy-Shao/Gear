package com.github.andyshao.reflect;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ParameterOperationTest {
    static class MyClass {
        public void myMethod(String argOne) {
        }
    }

    interface MyInterface {
        public void myMethod(String argTwo);
    }

    static class MyClass2 implements MyInterface {
        @Override
        public void myMethod(String argTwo) {
        }
    }

    @Test
    public void testGetParameterNames() {
        String[] ps = ParameterOperation
            .getMethodParamNames(MethodOperation.getMethod(MyClass.class , "myMethod" , String.class));
        Assert.assertThat(ps , Matchers.is(new String[] { "argOne" }));

        try {
            ps = ParameterOperation
                .getMethodParamNames(MethodOperation.getMethod(MyInterface.class , "myMethod" , String.class));
            Assert.fail();
        } catch (ReflectiveOperationException e) {
        }

        ps = ParameterOperation
            .getMethodParamNames(MethodOperation.getMethod(MyClass2.class , "myMethod" , String.class));
        Assert.assertThat(ps , Matchers.is(new String[] { "argTwo" }));
    }
}
