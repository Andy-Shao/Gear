package com.github.andyshao.proxy;

//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.InvocationHandler;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

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
public interface CglibPF<T> extends ProxyFactory<T> {
    @Override
    public default T getProxy(T target) {
        return this.getProxy(target , (Object proxy , Method method , Object[] args) -> CglibPF.this.invoke(target , method , args));
    }

    /**
     * get proxy
     * @param target target object
     * @param invocationHandler proxy handler
     * @return new object
     */
    @SuppressWarnings("unchecked")
    public default T getProxy(T target , InvocationHandler invocationHandler) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setInterfaces(this.proxyInterfaces(target));
        enhancer.setCallback(invocationHandler);
        return (T) enhancer.create();
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
    public abstract Object invoke(T target , Method method , Object[] args) throws Throwable;
}
