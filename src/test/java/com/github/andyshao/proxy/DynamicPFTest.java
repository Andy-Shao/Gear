package com.github.andyshao.proxy;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.proxy.DynamicProxyFactoryTest.MyInterface;
import com.github.andyshao.reflect.Reflects;

public class DynamicPFTest {

    @Test
    public void test() {
        DynamicProxyFactoryTest.MyInterface myInterface = new DynamicProxyFactoryTest.MyInterface() {

            @Override
            public boolean isAllow() {
                return false;
            }
        };

        Assert.assertFalse(myInterface.isAllow());

        DynamicPF<DynamicProxyFactoryTest.MyInterface> dynamicProxyF =
            new DynamicPF<DynamicProxyFactoryTest.MyInterface>() {
                private final String key = ProxyFactory.buildMethodKey(Reflects.getMethod(
                    DynamicProxyFactoryTest.MyInterface.class , "isAllow"));

                @Override
                public Object invoke(MyInterface target , Method method , Object[] args) throws Throwable {
                    return true;
                }

                @Override
                public Class<?>[] proxyInterfaces(MyInterface target) {
                    return ProxyFactory.<MyInterface> allInterfaces(target);
                }

                @Override
                public boolean proxyMethods(MyInterface target , Method method , Object[] args) {
                    if (ProxyFactory.buildMethodKey(method).equals(this.key)) return true;
                    return false;
                }
            };
        myInterface = dynamicProxyF.toProxyFactory().getProxy(myInterface);

        Assert.assertTrue(myInterface.isAllow());
    }
}
