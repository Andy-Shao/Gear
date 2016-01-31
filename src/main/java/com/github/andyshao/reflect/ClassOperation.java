package com.github.andyshao.reflect;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;

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
public final class ClassOperation {
    /**
     * 
     * @param className the name of class
     * @param <T> the type of class
     * @return the type of class
     * @see Class#forName(String)
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {
            throw new ClassNotFoundException(e);
        }
    }

    public static <T> T newInstance(Class<T> clazz) {
        return ConstructorOperation.newInstance(clazz);
    }

    public static <T> T newInstance(Class<T> clazz , Object... args) {
        Class<?>[] argTypes = new Class<?>[args.length];
        for (int i = 0 ; i < args.length ; i++)
            argTypes[i] = args[i].getClass();
        return ConstructorOperation.newInstance(ConstructorOperation.getConstructor(clazz , argTypes) , args);
    }

    @SuppressWarnings("unchecked")
    public static <T> T newInterfaceInstance(Class<T> clazz) {
        if (!clazz.isInterface()) throw new IllegalArgumentException("clazz is not interface");
        return (T) Proxy.newProxyInstance(clazz.getClassLoader() , new Class<?>[] { clazz } ,
            (proxy , method , args) -> {
                if (method.isDefault()) return "isDefault";
                else if (method.getReturnType().isAssignableFrom(int.class)) return 0;
                else if (method.getReturnType().isAssignableFrom(char.class)) return (char) 0;
                else if (method.getReturnType().isAssignableFrom(short.class)) return (short) 0;
                else if (method.getReturnType().isAssignableFrom(long.class)) return 0L;
                else if (method.getReturnType().isAssignableFrom(float.class)) return 0.0f;
                else if (method.getReturnType().isAssignableFrom(double.class)) return 0.0d;
                else if (method.getReturnType().isAssignableFrom(boolean.class)) return false;
                else if (method.getReturnType().isAssignableFrom(byte.class)) return (byte) 0;
                return null;
            });
    }

    /**
     * if the clazz has the super class then find the intefaces from super
     * class.
     * 
     * @param clazz The type of Object's
     * @param set the collection which store all of interfaces about the clazz.
     * @see Class#getInterfaces()
     */
    public static void superGetInterfaces(Class<?> clazz , Set<Class<?>> set) {
        set.addAll(Arrays.asList(clazz.getInterfaces()));
        if (clazz.getSuperclass() != null) Reflects.superGetInterfaces(clazz.getSuperclass() , set);
    }

    private ClassOperation() {
        throw new AssertionError("No support instance " + ClassOperation.class + " for you!");
    }
}
