package com.github.andyshao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 8, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> the type of proxied class
 */
@FunctionalInterface
public interface DynamicPF<T> extends ProxyFactory<T> {
    @Override
    public default T getProxy(T target) {
        return this.getProxy(target , (Object proxy , Method method , Object[] args) -> DynamicPF.this.invoke(target , method , args));
    }

    /**
     * get proxy
     * @param target target object
     * @param invocationHandler proxy handler
     * @return new object
     */
    @SuppressWarnings("unchecked")
    public default T getProxy(T target , InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader() , this.proxyInterfaces(target) , invocationHandler);
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
