package com.github.andyshao.proxy;

import com.github.andyshao.reflect.MethodOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CglibPFTest {

    static class MyClass {
        public boolean isAllow() {
            return false;
        }
    }

    @Test
    public void test() {
        final CglibPFTest.MyClass myClass = new CglibPFTest.MyClass();
        assertFalse(myClass.isAllow());

        final String methodName = ProxyFactory.buildMethodKey(MethodOperation.getMethod(CglibPFTest.MyClass.class , "isAllow"));
        CglibPF<CglibPFTest.MyClass> cglibProxyF = (target , method , args) -> {
            if (methodName.equals(ProxyFactory.buildMethodKey(method))) return true;
            return method.invoke(myClass , args);
        };

        assertTrue(cglibProxyF.getProxy(myClass).isAllow());
    }
}
