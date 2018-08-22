package com.github.andyshao.reflect;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.github.andyshao.reflect.annotation.Generic;
import com.github.andyshao.reflect.annotation.MethodInfo;
import com.github.andyshao.util.CollectionOperation;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 24, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
@SuppressWarnings("deprecation")
public final class MethodOperation {
    public static Method[] getAllMethods(Class<?> clazz) {
        Set<Method> result = new HashSet<Method>();
        CollectionOperation.addAll(result , clazz.getMethods());
        CollectionOperation.addAll(result , clazz.getDeclaredMethods());
        return result.toArray(new Method[result.size()]);
    }
    
    public static Method[] superGetMethods(Class<?> clazz) {
        ConcurrentHashMap<String , Method> cache = new ConcurrentHashMap<>();
        superGetMethods1(cache , clazz);
        return cache.values().toArray(new Method[cache.size()]);
    }
    
    protected static void superGetMethods1(Map<String, Method> cache, Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if(superclass != null) superGetMethods1(cache , superclass);
        Stream.of(clazz.getMethods()).forEach(method -> {
            StringBuilder key = new StringBuilder().append(method.getName()).append(":");
            for(Class<?> pType : method.getParameterTypes()) {
                key.append(pType.getName()).append(":");
            }
            cache.put(key.toString() , method);
        });
    }
    
    public static Method[] superGetDeclaredMethods(Class<?> clazz) {
        ConcurrentHashMap<String , Method> cache = new ConcurrentHashMap<>();
        superGetDeclaredMethods1(cache , clazz);
        return cache.values().toArray(new Method[cache.size()]);
    }
    
    protected static void superGetDeclaredMethods1(Map<String, Method> cache, Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if(superclass != null) superGetDeclaredMethods1(cache , superclass);
        Stream.of(clazz.getDeclaredMethods()).forEach(method -> {
            StringBuilder key = new StringBuilder().append(method.getName()).append(":");
            for(Class<?> pType : method.getParameterTypes()) {
                key.append(pType.getName()).append(":");
            }
            cache.put(key.toString() , method);
        });
    }

    /**
     * 
     * @param clazz the type of class
     * @param method_name the name of method
     * @param parameterTypes the type of parameters
     * @return the method of class
     * @see Class#getDeclaredMethod(String, Class...)
     */
    public static Method getDeclaredMethod(Class<?> clazz , String method_name , Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(method_name , parameterTypes);
        } catch (java.lang.NoSuchMethodException | java.lang.SecurityException e) {
            if (e instanceof java.lang.SecurityException) throw new SecurityException(e);
            else throw new NoSuchMethodException(e);
        }
    }

    /**
     * Only could find out the permission of method is public.
     * 
     * @param clazz the type of object
     * @param method_name the name of method
     * @param parameterTypes the parameter types list
     * @return the define of method
     * @see Class#getMethod(String, Class...)
     */
    public static Method getMethod(Class<?> clazz , String method_name , Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(method_name , parameterTypes);
        } catch (java.lang.NoSuchMethodException | java.lang.SecurityException e) {
            if (e instanceof java.lang.SecurityException) throw new SecurityException(e);
            else throw new NoSuchMethodException(e);
        }
    }

    @Deprecated
    public static GenericInfo getMethodGenericInfo(Method method) {
        MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
        if (methodInfo == null) throw new ReflectiveOperationException("Cannot find " + MethodInfo.class + " Type");
        Generic generic = methodInfo.methodGenericInfo();
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.isGeneiric = generic.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
        genericInfo.declareType = method.getDeclaringClass();
        return genericInfo;
    }

    @Deprecated
    public static GenericInfo getReturnGenericInfo(Method method) {
        MethodInfo methodInfo = method.getAnnotation(MethodInfo.class);
        if (methodInfo == null) throw new ReflectiveOperationException("Cannot find " + MethodInfo.class + " Type");
        Generic annotation = methodInfo.returnGenericInfo();
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.declareType = method.getReturnType();
        genericInfo.isGeneiric = annotation.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(annotation.componentTypes());
        return genericInfo;
    }

    /**
     * 
     * @param target the object which should run the method
     * @param method the define of method
     * @param values the values of parameters of method's
     * @param <T> the type of return
     * @return the result of method's
     * @see Method#invoke(Object, Object...)
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoked(Object target , Method method , Object... values) {
        try {
            return (T) method.invoke(target , values);
        } catch (java.lang.IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            if (e instanceof java.lang.IllegalAccessException) throw new IllegalAccessException(e);
            else throw new InvocationTargetException(e);
        }
    }

    /**
     * if the clazz doesn't include the method, it will check the super class.
     * 
     * @param clazz the type of class
     * @param method_name the name of method
     * @param parameterTypes the type of parameters
     * @return the method of class
     * @see Class#getDeclaredMethod(String, Class...)
     */
    public static Method superGetDeclaredMethod(Class<?> clazz , String method_name , Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(method_name , parameterTypes);
        } catch (java.lang.NoSuchMethodException e) {
            if (clazz.getSuperclass() != null) return MethodOperation.superGetDeclaredMethod(clazz.getSuperclass() , method_name , parameterTypes);
            throw new NoSuchMethodException(e);
        } catch (java.lang.SecurityException e) {
            throw new SecurityException(e);
        }
    }

    private MethodOperation() {
        throw new AssertionError("No support instance " + MethodOperation.class + " for you!");
    }
}
