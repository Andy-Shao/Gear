package com.github.andyshao.reflect;

import java.lang.reflect.Constructor;

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
public class ConstructorOperation {
    /**
     * 
     * @param clazz the type of class
     * @param parameterTypes the type of parameters of constructor of class
     * @param <T> the type of class
     * @return the constructor of class
     * @see Class#getConstructor(Class...)
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz , Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (java.lang.NoSuchMethodException | java.lang.SecurityException e) {
            if (e instanceof java.lang.NoSuchMethodException) throw new NoSuchFieldException(e);
            else throw new SecurityException(e);
        }
    }

    /**
     * 
     * @param clazz the type of class
     * @param parameterTypes the typ of parameters of constructor of class
     * @param <T> the type of class
     * @return the constructor of class
     * @see Class#getDeclaredConstructor(Class...)
     */
    public static <T> Constructor<T> getDeclaredConstructor(Class<T> clazz , Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (java.lang.NoSuchMethodException | java.lang.SecurityException e) {
            if (e instanceof java.lang.NoSuchMethodException) throw new NoSuchFieldException(e);
            else throw new SecurityException(e);
        }
    }

    /**
     * 
     * @param clazz the class type
     * @param <T> the object type
     * @return the object which come from clazz
     * @see Class#newInstance()
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (java.lang.InstantiationException | java.lang.IllegalAccessException e) {
            if (e instanceof java.lang.InstantiationException) throw new InstantiationException(e);
            else throw new IllegalAccessException(e);
        }
    }

    /**
     * 
     * @param constructor the define of class' constructor
     * @param values the values of parameters of constructor's
     * @param <T> the type of return
     * @return the object which is builded from constructor
     * @see Constructor#newInstance(Object...)
     */
    public static <T> T newInstance(Constructor<T> constructor , Object... values) {
        try {
            return constructor.newInstance(values);
        } catch (java.lang.InstantiationException | java.lang.IllegalAccessException
            | java.lang.reflect.InvocationTargetException e) {
            if (e instanceof java.lang.InstantiationException) throw new InstantiationException(e);
            else if (e instanceof java.lang.IllegalAccessException) throw new IllegalAccessException(e);
            else throw new InvocationTargetException(e);
        }
    }

    private ConstructorOperation() {
        throw new AssertionError("No support instance " + ConstructorOperation.class + " for you!");
    }
}
