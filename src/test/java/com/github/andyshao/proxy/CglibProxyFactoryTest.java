package com.github.andyshao.proxy;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.Reflects;

public class CglibProxyFactoryTest {

    static class MyClass {
        public boolean isAllow() {
            return false;
        }
    }

    @Test
    public void test() {
        MyClass myClass = new MyClass();

        Assert.assertFalse(myClass.isAllow());

        CglibProxyFactory<MyClass> cglibProxyFactory = new CglibProxyFactory<MyClass>() {
            private final String methodName = ProxyFactory
                .buildMethodKey(Reflects.getMethod(MyClass.class , "isAllow"));

            @Override
            protected Object invoke(MyClass target , Method method , Object[] args) throws Throwable {
                return true;
            }

            @Override
            protected boolean proxyMethods(MyClass target , Method method , Object[] args) {
                if (this.methodName.equals(ProxyFactory.buildMethodKey(method))) return true;
                return false;
            }
        };
        myClass = cglibProxyFactory.getProxy(myClass);

        Assert.assertTrue(myClass.isAllow());
    }
}
