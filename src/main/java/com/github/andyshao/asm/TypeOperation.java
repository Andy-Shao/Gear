package com.github.andyshao.asm;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

import org.objectweb.asm.Type;

import com.github.andyshao.reflect.ClassOperation;
import com.github.andyshao.reflect.MethodOperation;
import com.github.andyshao.reflect.NoSuchMethodException;

/**
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 6, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class TypeOperation {
    public static final Class<?>[] getArgumentClasses(String descriptor) {
        return TypeOperation.getArgumentClasses(Type.getType(descriptor));
    }

    public static final Class<?>[] getArgumentClasses(Type type) {
        if (type.getSort() != Type.METHOD) throw new ASMOperationException("Type should be method type");
        Type[] types = type.getArgumentTypes();
        Class<?>[] result = new Class<?>[types.length];
        for (int i = 0 ; i < types.length ; i++)
            result[i] = TypeOperation.getClass(types[i]);
        return result;
    }

    public static final Class<?> getClass(Type type) {
        Class<?> result = null;
        switch (type.getSort()) {
        case Type.BOOLEAN:
            result = boolean.class;
            break;
        case Type.BYTE:
            result = byte.class;
            break;
        case Type.CHAR:
            result = char.class;
            break;
        case Type.DOUBLE:
            result = double.class;
            break;
        case Type.FLOAT:
            result = float.class;
            break;
        case Type.INT:
            result = int.class;
            break;
        case Type.LONG:
            result = long.class;
            break;
        case Type.ARRAY: {
            String descriptor = type.getDescriptor();
            Type tmp = Type.getType(descriptor.substring(1));
            result = Array.newInstance(TypeOperation.getClass(tmp) , 0).getClass();
            break;
        }
        case Type.METHOD:
            throw new ASMOperationException("Method is not allowed");
        case Type.OBJECT: {
            String descriptor = type.getDescriptor();
            String className = descriptor.substring(1 , descriptor.length() - 1).replace('/' , '.');
            result = ClassOperation.forName(className);
            break;
        }
        case Type.SHORT:
            result = short.class;
            break;
        case Type.VOID:
            result = void.class;
            break;
        default:
            break;
        }
        return result;
    }

    public static final Method getMethod(String descriptor , String methodName , Class<?> superClass) {
        return TypeOperation.getMethod(Type.getType(descriptor) , methodName , superClass);
    }

    public static final Method getMethod(Type type , String methodName , Class<?> superClass) {
        if (type.getSort() != Type.METHOD) throw new ASMOperationException("Type should be method type");
        final Class<?>[] arguments = TypeOperation.getArgumentClasses(type.getDescriptor());
        Method method = null;
        try {
            method = MethodOperation.getMethod(superClass , methodName , arguments);
        } catch (NoSuchMethodException e) {
            try {
                method = MethodOperation.superGetDeclaredMethod(superClass , methodName , arguments);
            } catch (NoSuchMethodException e1) {
                throw new ASMOperationException("Cannot find out the method" , e1);
            }
        }
        return method;
    }

    public static final Class<?> getReturnClass(String descriptor) {
        Type type = Type.getType(descriptor).getReturnType();
        return TypeOperation.getClass(type);
    }

    public static final Class<?> getReturnClass(Type type) {
        if (type.getSort() != Type.METHOD) throw new ASMOperationException("Type should be method type");
        return TypeOperation.getClass(type.getReturnType());
    }

    private TypeOperation() {
        throw new AssertionError("No " + TypeOperation.class + " instance for you");
    }

}
