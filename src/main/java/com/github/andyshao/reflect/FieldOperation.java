package com.github.andyshao.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import com.github.andyshao.asm.ApiConfs;
import com.github.andyshao.asm.TypeOperation;
import com.github.andyshao.reflect.SignatureDetector.ClassSignature;
import com.github.andyshao.reflect.annotation.Generic;
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
public final class FieldOperation {
    public static Field[] getAllField(Class<?> clazz) {
        Set<Field> result = getAllFieldForSet(clazz);
        return result.toArray(new Field[result.size()]);
    }

	public static Set<Field> getAllFieldForSet(Class<?> clazz) {
		Set<Field> result = new HashSet<>();
        getAllFieldForSet(clazz, result);
		return result;
	}

	static void getAllFieldForSet(Class<?> clazz, Set<Field> result) {
		CollectionOperation.addAll(result , clazz.getFields());
        CollectionOperation.addAll(result , clazz.getDeclaredFields());
	}
    
    public static Set<Field> superGetAllFieldForSet(Class<?> clazz) {
    	Set<Field> result = new HashSet<>();
    	superGetAllFieldForSet(clazz, result);
    	return result;
    }
    
    static void superGetAllFieldForSet(Class<?> clazz, Set<Field> set) {
    	getAllFieldForSet(clazz, set);
    	Class<?> superclass = clazz.getSuperclass();
		if(superclass != null) {
    		getAllFieldForSet(superclass, set);
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

    @Deprecated
    public static final GenericInfo getFieldGenericInfo(Field field) {
        Generic generic = field.getAnnotation(Generic.class);
        if (generic == null) throw new ReflectiveOperationException("Cannot find " + Generic.class);
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.isGeneiric = generic.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
        genericInfo.declareType = field.getType();
        return genericInfo;
    }
    
    public static final GenericNode getFieldTypeInfo(Field field, ClassSignature classSignature) {
        final GenericNode result = new GenericNode();
        String singnature = classSignature.fieldSignatures.get(field);
        if(singnature == null) {
            result.setDeclareType(field.getType());
            return result;
        }
        SignatureReader reader = new SignatureReader(singnature);
        reader.accept(new SignatureVisitor(ApiConfs.DEFAULT_ASM_VERSION) {
            private volatile boolean isArray = false;
            private volatile GenericNode currentNode = result;
            
            @Override
            public void visitBaseType(char descriptor) {
                Class<?> clazz = TypeOperation.getClass(Type.getType(String.valueOf(descriptor)));
                currentNode.setDeclareType(clazz);
                super.visitBaseType(descriptor);
            }

            @Override
            public SignatureVisitor visitArrayType() {
                isArray = true;
                return super.visitArrayType();
            }

            @Override
            public void visitEnd() {
                GenericNode parent = currentNode.getParent();
                if(parent != null) currentNode = parent;
                super.visitEnd();
            }

            @Override
            public void visitClassType(String name) {
                Class<?> clazz = ClassOperation.forName(name.replace('/' , '.'));
                if(isArray) {
                    clazz = Array.newInstance(clazz , 0).getClass();
                    isArray = false;
                }
                currentNode.setDeclareType(clazz);
                super.visitClassType(name);
            }

            @Override
            public SignatureVisitor visitTypeArgument(char wildcard) {
                GenericNode node = new GenericNode();
                node.setParent(currentNode);
                currentNode.setGeneiric(true);
                currentNode.getComponentTypes().add(node);
                currentNode = node;
                return super.visitTypeArgument(wildcard);
            }

            @Override
            public void visitTypeVariable(String name) {
                currentNode.setTypeVariable(name);
                GenericNode parent = currentNode.getParent();
                if(parent != null) currentNode = parent;
                super.visitTypeVariable(name);
            }
        });
        return result;
    }
    
    public static final GenericNode getFieldTypeInfo(Field field) {
        return getFieldTypeInfo(field , new SignatureDetector(ApiConfs.DEFAULT_ASM_VERSION).getSignature(field.getDeclaringClass()));
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
