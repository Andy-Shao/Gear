package com.github.andyshao.proxy;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.Reflects;

public class DynamicProxyFactoryTest {

    static interface MyInterface {
        boolean isAllow();
    }

    @Test
    public void test() {
        MyInterface myInterface = new MyInterface() {
            @Override
            public boolean isAllow() {
                return false;
            }
        };

        Assert.assertFalse(myInterface.isAllow());

        DynamicProxyFactory<MyInterface> dynamicProxyFactory =
            new DynamicProxyFactory<DynamicProxyFactoryTest.MyInterface>() {
                private final String key = ProxyFactory.buildMethodKey(Reflects.getMethod(
                    DynamicProxyFactoryTest.MyInterface.class , "isAllow"));

                @Override
                protected Object invoke(MyInterface target , Method method , Object[] args) throws Throwable {
                    return true;
                }

                @Override
                protected boolean proxyMethods(MyInterface target , Method method , Object[] args) {
                    if (ProxyFactory.buildMethodKey(method).equals(this.key)) return true;
                    return false;
                }
            };
        myInterface = dynamicProxyFactory.getProxy(myInterface);

        Assert.assertTrue(myInterface.isAllow());
    }
}
