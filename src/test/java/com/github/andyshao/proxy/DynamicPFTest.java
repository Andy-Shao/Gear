package com.github.andyshao.proxy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
        assertFalse(myInterface.isAllow());
        final String key = ProxyFactory.buildMethodKey(MethodOperation.getMethod(DynamicPFTest.MyInterface.class , "isAllow"));

        DynamicPF<MyInterface> dynamicPF = (target , method , args) -> {
            if (key.equals(ProxyFactory.buildMethodKey(method))) return true;
            return method.invoke(myInterface , args);
        };

        assertTrue(dynamicPF.getProxy(myInterface).isAllow());
    }
}
