package com.github.andyshao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@FunctionalInterface
public interface DynamicPF<T> extends ProxyFactory<T> {
    @SuppressWarnings("unchecked")
    public default T getProxy(T target , InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader() , this.proxyInterfaces(target) ,
            invocationHandler);
    }

    @Override
    public default T getProxy(T target) {
        return this.getProxy(target ,
            (Object proxy , Method method , Object[] args) -> DynamicPF.this.invoke(target , method , args));
    }

    /**
     * when the method which will be invoke should be proxy.
     * this method will be run.
     * 
     * @param target the target which will be proxy
     * @param method the method which will be invoke
     * @param args the args of method
     * @return the answer of method
     * @throws Throwable andy exception when run this method
     */
    public Object invoke(T target , Method method , Object[] args) throws Throwable;
}
