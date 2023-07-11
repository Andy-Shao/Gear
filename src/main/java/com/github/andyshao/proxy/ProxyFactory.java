package com.github.andyshao.proxy;

import com.github.andyshao.reflect.ClassOperation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Title:public proxy factory interface<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 17, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <T> the type of target which will be proxy
 */
@FunctionalInterface
public interface ProxyFactory<T> {
    /**
     * build method key
     * @param method {@link Method}
     * @return the method key
     */
    public static String buildMethodKey(Method method) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method.getReturnType()).append(":");
        stringBuilder.append(method.getName()).append(":");
        stringBuilder.append(Arrays.toString(method.getParameterTypes()));
        return stringBuilder.toString();
    }

    /**
     * get the proxy
     * 
     * @param target the target which will be proxy
     * @return the proxy
     */
    T getProxy(T target);

    /**
     * proxy interfaces
     * @param target target object
     * @return all interfaces of target
     */
    public default Class<?>[] proxyInterfaces(T target) {
        Set<Class<?>> set = new HashSet<>();
        ClassOperation.superGetInterfaces(target.getClass() , set);
        return set.toArray(new Class<?>[set.size()]);
    }
}
