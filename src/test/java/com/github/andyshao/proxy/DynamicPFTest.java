package com.github.andyshao.proxy;

import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.MethodOperation;

public class DynamicPFTest {

    static interface MyInterface {
        boolean isAllow();
    }

    @Test
    public void test() {
        final DynamicPFTest.MyInterface myInterface = new DynamicPFTest.MyInterface() {

            @Override
            public boolean isAllow() {
                return false;
            }
        };
        Assert.assertFalse(myInterface.isAllow());
        final String key = ProxyFactory.buildMethodKey(MethodOperation.getMethod(DynamicPFTest.MyInterface.class , "isAllow"));

        DynamicPF<MyInterface> dynamicPF = (target , method , args) -> {
            if (key.equals(ProxyFactory.buildMethodKey(method))) return true;
            return method.invoke(myInterface , args);
        };

        Assert.assertTrue(dynamicPF.getProxy(myInterface).isAllow());
    }
}
