package com.github.andyshao.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
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
    @SuppressWarnings("unchecked")
    public static <T> Class<T> forName(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (java.lang.ClassNotFoundException e) {
            throw new ClassNotFoundException(e);
        }
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
     * @param clazz the type of class
     * @param field_name the name of field
     * @return the field of class
     * @see Class#getDeclaredField(String)
     */
    public static Field getDeclaredField(Class<?> clazz , String field_name) {
        try {
            return clazz.getDeclaredField(field_name);
        } catch (java.lang.NoSuchFieldException | java.lang.SecurityException e) {
            if (e instanceof java.lang.SecurityException) throw new SecurityException(e);
            else throw new NoSuchFieldException(e);
        }
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
            else throw new NoSuchFieldException(e);
        }
    }

    /**
     * 
     * @param clazz the type of class
     * @param field_name the name of field
     * @return the fields of class
     * @see Class#getField(String)
     */
    public static Field getField(Class<?> clazz , String field_name) {
        try {
            return clazz.getField(field_name);
        } catch (java.lang.NoSuchFieldException | java.lang.SecurityException e) {
            if (e instanceof java.lang.SecurityException) throw new SecurityException(e);
            else throw new NoSuchFieldException(e);
        }
    }

    /**
     * 
     * @param target the Object which store the value of field
     * @param field the define of field
     * @param <T> the type of return
     * @return the value of field
     * @see Field#get(Object)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object target , Field field) {
        try {
            return (T) field.get(target);
        } catch (java.lang.IllegalAccessException e) {
            throw new IllegalAccessException(e);
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
            else throw new NoSuchFieldException(e);
        }
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

    /**
     * 
     * @param target the object which has define and value about field
     * @param field the define of field
     * @param value the values of parameters of constructor's
     * @see Field#set(Object, Object)
     */
    public static void setFieldValue(Object target , Field field , Object value) {
        try {
            field.set(target , value);
        } catch (java.lang.IllegalAccessException e) {
            throw new IllegalAccessException(e);
        }
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
        T annotation = target.getAnnotation(clazz);
        if (annotation != null) return annotation;

        if (target.isInterface()) {
            Class<?>[] interfaces = target.getInterfaces();
            for (Class<?> _interface : interfaces) {
                annotation = Reflects.superGetAnnotation(_interface , clazz);
                if (annotation != null) return annotation;
            }
        } else {
            Class<? extends Object> superClass = target.getSuperclass();
            if (superClass != null) {
                annotation = Reflects.superGetAnnotation(superClass , clazz);
                if (annotation != null) return annotation;
            }

            Class<?>[] interfaces = target.getInterfaces();
            for (Class<?> _interface : interfaces) {
                annotation = Reflects.superGetAnnotation(_interface , clazz);
                if (annotation != null) return annotation;
            }
        }

        return annotation;
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
        try {
            return clazz.getDeclaredField(field_name);
        } catch (java.lang.NoSuchFieldException e) {
            if (clazz.getSuperclass() != null)
                return Reflects.superGetDeclaredField(clazz.getSuperclass() , field_name);
            throw new NoSuchFieldException(e);
        } catch (java.lang.SecurityException e) {
            throw new SecurityException(e);
        }
    }

    /**
     * 
     * @param clazz the type of class
     * @return the fields of class
     * @see Class#getDeclaredField(String)
     */
    public static Field[] superGetDeclaredFields(Class<?> clazz) {
        Field[] result = new Field[0];
        if (clazz.getSuperclass() != null) {
            Field[] fields = Reflects.superGetDeclaredFields(clazz.getSuperclass());
            result = ArrayOperation.mergeArray(Field[].class , result , fields);
        }
        Field[] fields = clazz.getDeclaredFields();
        result = ArrayOperation.mergeArray(Field[].class , result , fields);

        return result;
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
        try {
            return clazz.getDeclaredMethod(method_name , parameterTypes);
        } catch (java.lang.NoSuchMethodException e) {
            if (clazz.getSuperclass() != null)
                return Reflects.superGetDeclaredMethod(clazz.getSuperclass() , method_name , parameterTypes);
            throw new NoSuchFieldException(e);
        } catch (java.lang.SecurityException e) {
            throw new SecurityException(e);
        }
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

    private Reflects() {
        throw new AssertionError("No support instance " + Reflects.class.getName());
    }
}
