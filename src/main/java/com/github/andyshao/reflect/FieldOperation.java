package com.github.andyshao.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.github.andyshao.reflect.annotation.Generic;

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
public final class FieldOperation {
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

    public static final GenericInfo getFieldGenericInfo(Field field) {
        Generic generic = field.getAnnotation(Generic.class);
        if (generic == null) throw new ReflectiveOperationException("Cannot find " + Generic.class);
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.isGeneiric = generic.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
        genericInfo.declareType = field.getType();
        return genericInfo;
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

    public static final Object getValueByGetMethod(Object target , String paramName) {
        final String methodName = "get" + paramName.substring(0 , 1).toUpperCase() + paramName.substring(1);
        final Method method = MethodOperation.getMethod(target.getClass() , methodName);
        return MethodOperation.invoked(target , method);
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

    public static final void setValueBySetMethod(Object target , String paramName , Class<?> paramType , Object value) {
        final String methodName = "set" + paramName.substring(0 , 1).toUpperCase() + paramName.substring(1);
        final Method method = MethodOperation.getMethod(target.getClass() , methodName , paramType);
        MethodOperation.invoked(target , method , value);
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
            if (clazz.getSuperclass() != null) return FieldOperation.superGetDeclaredField(clazz.getSuperclass() , field_name);
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
            Field[] fields = FieldOperation.superGetDeclaredFields(clazz.getSuperclass());
            result = ArrayOperation.mergeArray(Field[].class , result , fields);
        }
        Field[] fields = clazz.getDeclaredFields();
        result = ArrayOperation.mergeArray(Field[].class , result , fields);

        return result;
    }

    private FieldOperation() {
        throw new AssertionError("No support instance " + FieldOperation.class + " for you!");
    }

}
