package com.github.andyshao.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 
 * Title:the tool of java.lang.reflect<br>
 * Descript:<br>
 * With a easy way to use java.lang.reflect.*; For this class could avoid some
 * exception which is
 * the subclass of {@link Exception}.<br>
 * Convert the {@link Exception} or the subclass of {@link Exception} to
 * {@link RuntimeException}.
 * For example: <br>
 * <blockquote>
 * 
 * <pre>
 * try {
 *     return (Class&lt;T&gt;) Class.forName(className);
 * } catch (ClassNotFoundException e) {
 *     throw new RuntimeException(e);
 * }
 * </pre>
 * 
 * </blockquote> <br>
 * Any necessary about take the original exception, you could use this way: <br>
 * <blockquote>
 * 
 * <pre>
 * try {
 *     Class&lt;String&gt; clazz = Reflects.forName(&quot;java.lang.String&quot;);
 * } catch (RuntimeException e) {
 *     Throwable throwable = e.getCause();
 *     if (throwable instanceof ClassNotFoundException) {
 *         // Do something
 *     }
 *     // For other things:
 *     throw e;
 * }
 * </pre>
 * 
 * </blockquote>
 * <p>
 * Copyright: Copryright(c) Mar 7, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class Reflects {
    /**
     * 
     * @param className the name of class
     * @param <T> the type of class
     * @return the type of class
     * @see Class#forName(String)
     */
    public static <T> Class<T> forName(String className) {
        return ClassOperation.forName(className);
    }

    /**
     * 
     * @param clazz the type of class
     * @param parameterTypes the type of parameters of constructor of class
     * @param <T> the type of class
     * @return the constructor of class
     * @see Class#getConstructor(Class...)
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz , Class<?>... parameterTypes) {
        return ConstructorOperation.getConstructor(clazz , parameterTypes);
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
        return ConstructorOperation.getDeclaredConstructor(clazz , parameterTypes);
    }

    /**
     * 
     * @param clazz the type of class
     * @param field_name the name of field
     * @return the field of class
     * @see Class#getDeclaredField(String)
     */
    public static Field getDeclaredField(Class<?> clazz , String field_name) {
        return FieldOperation.getDeclaredField(clazz , field_name);
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
        return MethodOperation.getDeclaredMethod(clazz , method_name , parameterTypes);
    }

    /**
     * 
     * @param clazz the type of class
     * @param field_name the name of field
     * @return the fields of class
     * @see Class#getField(String)
     */
    public static Field getField(Class<?> clazz , String field_name) {
        return FieldOperation.getField(clazz , field_name);
    }

    /**
     * 
     * @param target the Object which store the value of field
     * @param field the define of field
     * @param <T> the type of return
     * @return the value of field
     * @see Field#get(Object)
     */
    public static <T> T getFieldValue(Object target , Field field) {
        return FieldOperation.getFieldValue(target , field);
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
        return MethodOperation.getMethod(clazz , method_name , parameterTypes);
    }

    /**
     * 
     * <p>
     * Gets the parameter name of method's
     * </p>
     *
     * @param m method
     * @return the parameter name list
     */
    public static String[] getMethodParamNames(final Method m) {
        return ParameterOperation.getMethodParamNames(m);
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
    public static <T> T invoked(Object target , Method method , Object... values) {
        return MethodOperation.invoked(target , method , values);
    }

    /**
     * 
     * @param clazz the class type
     * @param <T> the object type
     * @return the object which come from clazz
     * @see Class#newInstance()
     */
    public static <T> T newInstance(Class<T> clazz) {
        return ConstructorOperation.newInstance(clazz);
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
        return ConstructorOperation.newInstance(constructor , values);
    }

    /**
     * 
     * @param target the object which has define and value about field
     * @param field the define of field
     * @param value the values of parameters of constructor's
     * @see Field#set(Object, Object)
     */
    public static void setFieldValue(Object target , Field field , Object value) {
        FieldOperation.setFieldValue(target , field , value);
    }

    /**
     * 
     * @param target the object which has define and value about field
     * @param clazz the type of object
     * @param <T> the type of return
     * @return all of annotation
     * @see Class#getAnnotation(Class)
     */
    public static <T extends Annotation> T superGetAnnotation(Class<? extends Object> target , Class<T> clazz) {
        return AnnotationOperation.superGetAnnotation(target , clazz);
    }

    /**
     * if the clazz doesn't include the mtheod, it will check the super class.
     * 
     * @param clazz the type of class
     * @param field_name the name of field
     * @return the field of class
     * @see Class#getDeclaredField(String)
     */
    public static Field superGetDeclaredField(Class<?> clazz , String field_name) {
        return FieldOperation.superGetDeclaredField(clazz , field_name);
    }

    /**
     * 
     * @param clazz the type of class
     * @return the fields of class
     * @see Class#getDeclaredField(String)
     */
    public static Field[] superGetDeclaredFields(Class<?> clazz) {
        return FieldOperation.superGetDeclaredFields(clazz);
    }

    /**
     * if the clazz doesn't include the mtheod, it will check the super class.
     * 
     * @param clazz the type of class
     * @param method_name the name of method
     * @param parameterTypes the type of parameters
     * @return the method of class
     * @see Class#getDeclaredMethod(String, Class...)
     */
    public static Method superGetDeclaredMethod(Class<?> clazz , String method_name , Class<?>... parameterTypes) {
        return MethodOperation.superGetDeclaredMethod(clazz , method_name , parameterTypes);
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
        ClassOperation.superGetInterfaces(clazz , set);
    }

    private Reflects() {
        throw new AssertionError("No support instance " + Reflects.class.getName());
    }
}
