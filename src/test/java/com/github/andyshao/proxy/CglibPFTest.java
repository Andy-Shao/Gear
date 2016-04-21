package com.github.andyshao.proxy;

import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.MethodOperation;

public class CglibPFTest {

    static class MyClass {
        public boolean isAllow() {
            return false;
        }
    }

    @Test
    public void test() {
        final CglibPFTest.MyClass myClass = new CglibPFTest.MyClass();
        Assert.assertFalse(myClass.isAllow());

        final String methodName = ProxyFactory.buildMethodKey(MethodOperation.getMethod(CglibPFTest.MyClass.class , "isAllow"));
        CglibPF<CglibPFTest.MyClass> cglibProxyF = (target , method , args) -> {
            if (methodName.equals(ProxyFactory.buildMethodKey(method))) return true;
            return method.invoke(myClass , args);
        };

        Assert.assertTrue(cglibProxyF.getProxy(myClass).isAllow());
    }
}
