package com.github.andyshao.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.andyshao.util.Reflects;

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
     * get the proxy
     * 
     * @param target the target which will be proxy
     * @return the proxy
     */
    T getProxy(T target);
    
    public static <T> Class<?>[] allInterfaces(T target){
		Set<Class<?>> set = new HashSet<>();
	    Reflects.superGetInterfaces(target.getClass() , set);
	    return set.toArray(new Class<?>[set.size()]);
	}

	public static String buildMethodKey(Method method){
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append(method.getReturnType()).append(":");
    	stringBuilder.append(method.getName()).append(":");
    	stringBuilder.append(Arrays.toString(method.getParameterTypes()));
    	return stringBuilder.toString();
    }
}
