package com.github.andyshao.reflect;

import com.github.andyshao.asm.ApiConfs;
import com.github.andyshao.asm.TypeOperation;
import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.reflect.SignatureDetector.ClassSignature;
import com.github.andyshao.reflect.annotation.Generic;
import com.github.andyshao.reflect.annotation.MethodInfo;
import com.github.andyshao.util.CollectionOperation;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    /**
     * get set methods
     * @param clazz {@link Class}
     * @return the methods
     */
    public static final List<Method> getSetMethods(Class<?> clazz){
        return Arrays.stream(clazz.getMethods())
                .filter(method -> {
                    int modifiers = method.getModifiers();
                    if(!method.getName().startsWith("set")) return false;
                    if(!Modifier.isPublic(modifiers)) return false;
                    if(Modifier.isAbstract(modifiers)) return false;
                    if(Modifier.isStatic(modifiers)) return false;
                    if(!method.getReturnType().isAssignableFrom(void.class)) return false;
                    if(method.getParameterTypes().length != 1) return false;
                    return true;
                })
                .collect(Collectors.toList());
    }

    /**
     * get getter methods
     * @param clazz {@link Class}
     * @return methods
     */
    public static final List<Method> getGetMethods(Class<?> clazz){
        return Arrays.stream(clazz.getMethods())
                .filter(method -> {
                    if(!method.getName().startsWith("get")) return false;
                    int modifiers = method.getModifiers();
                    if(!Modifier.isPublic(modifiers)) return false;
                    if(Modifier.isStatic(modifiers)) return false;
                    if(Modifier.isAbstract(modifiers)) return false;
                    if(method.getReturnType().isAssignableFrom(void.class)) return false;
                    if(method.getParameterTypes().length != 0) return false;
                    return true;
                })
                .collect(Collectors.toList());
    }

    /**
     * get all methods
     * @param clazz {@link Class}
     * @return {@link Method} array
     */
    public static Method[] getAllMethods(Class<?> clazz) {
        Set<Method> result = new HashSet<Method>();
        CollectionOperation.addAll(result , clazz.getMethods());
        CollectionOperation.addAll(result , clazz.getDeclaredMethods());
        return result.toArray(new Method[result.size()]);
    }

    /**
     * get getter method (include supper class)
     * @param clazz {@link Class}
     * @return {@link Method} array
     */
    public static Method[] superGetMethods(Class<?> clazz) {
        ConcurrentHashMap<String , Method> cache = new ConcurrentHashMap<>();
        superGetMethods1(cache , clazz);
        return cache.values().toArray(new Method[cache.size()]);
    }

    /**
     * super get method s1
     * @param cache {@link Map}
     * @param clazz {@link Class}
     */
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

    /**
     * get declared methods (include super class)
     * @param clazz {@link Class}
     * @return {@link Method} array
     */
    public static Method[] superGetDeclaredMethods(Class<?> clazz) {
        ConcurrentHashMap<String , Method> cache = new ConcurrentHashMap<>();
        superGetDeclaredMethods1(cache , clazz);
        return cache.values().toArray(new Method[cache.size()]);
    }

    /**
     * super get declared method s1
     * @param cache {@link Map}
     * @param clazz {@link Class}
     */
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
     * get declared method
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

    /**
     * get method generic info
     * @param method {@link Method}
     * @return {@link GenericInfo}
     * @deprecated too complicated
     */
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

    /**
     * get return {@link GenericNode}
     * @param method {@link Method}
     * @return {@link GenericInfo}
     * @deprecated too complicated
     */
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
     * get return type information
     * @param method {@link Method}
     * @return {@link GenericNode}
     */
    public static GenericNode getReturnTypeInfo(Method method) {
        return getReturnTypeInfo(method , new SignatureDetector(ApiConfs.DEFAULT_ASM_VERSION).getSignature(method.getDeclaringClass()));
    }

    /**
     * get return type information
     * @param method {@link Method}
     * @return {@link GenericNode}
     */
    public static GenericNode getReturnTypeInfoByNative(Method method) {
        final java.lang.reflect.Type type = method.getGenericReturnType();
        final GenericNode result = ClassOperation.analysisGenericType(type);
        result.setDeclareType(method.getReturnType());
        return result;
    }

    /**
     * get return type information
     * @param method {@link Method}
     * @param classSignature {@link ClassSignature}
     * @return {@link GenericNode}
     */
    public static GenericNode getReturnTypeInfo(Method method, ClassSignature classSignature) {
        final GenericNode ret = new GenericNode();
        String signature = classSignature.methodSignatures.get(method);
        if(StringOperation.isTrimEmptyOrNull(signature)) {
            ret.setGeneiric(false);
            ret.setDeclareType(method.getReturnType());
            return ret;
        }
        SignatureReader reader = new SignatureReader(signature);
        reader.accept(new SignatureVisitor(ApiConfs.DEFAULT_ASM_VERSION) {
            private volatile boolean isReturn = false;
            private volatile boolean isArray = false;
            private volatile GenericNode currentSide = ret;
        
            @Override
            public SignatureVisitor visitReturnType() {
                isReturn = true;
                return super.visitReturnType();
            }
        
            @Override
            public void visitBaseType(char descriptor) {
                if(isReturn) {
                    Class<?> clazz = TypeOperation.getClass(Type.getType(String.valueOf(descriptor)));
                    currentSide.setDeclareType(clazz);
                }
                super.visitBaseType(descriptor);
            }
        
            @Override
            public void visitClassType(String name) {
                if(isReturn) {
                    Class<?> clazz = ClassOperation.forName(name.replace('/' , '.'));
                    if(isArray) {
                        clazz = Array.newInstance(clazz , 0).getClass();
                        isArray = false;
                    }
                    currentSide.setDeclareType(clazz);
                }
                super.visitClassType(name);
            }
        
            @Override
            public SignatureVisitor visitTypeArgument(char wildcard) {
                if(isReturn) {
                    GenericNode node = new GenericNode();
                    node.setParent(currentSide);
                    currentSide.getComponentTypes().add(node);
                    currentSide.setGeneiric(true);
                    currentSide = node;
                }
                return super.visitTypeArgument(wildcard);
            }
        
            @Override
            public void visitEnd() {
                if(isReturn) {
                    GenericNode parent = currentSide.getParent();
                    if(parent != null) currentSide = parent;
                }
                super.visitEnd();
            }
        
            @Override
            public SignatureVisitor visitArrayType() {
                if(isReturn) {
                    isArray = true;
                }
                return super.visitArrayType();
            }

            @Override
            public void visitTypeVariable(String name) {
                currentSide.setTypeVariable(name);
                GenericNode parent = currentSide.getParent();
                if(parent != null) currentSide = parent;
                super.visitTypeVariable(name);
            }
            
        });
        return ret;
    }

    /**
     * get parameter type information
     * @param method {@link Method}
     * @return {@link GenericNode} list
     */
    public static List<GenericNode> getParameterTypesInfo(Method method){
        return ParameterOperation.getParameterTypesInfo(method);
    }

    /**
     * get parameter type information
     * @param method {@link Method}
     * @param classSignature {@link ClassSignature}
     * @return {@link GenericNode} list
     */
    public static List<GenericNode> getParameterTypesInfo(Method method, ClassSignature classSignature){
        return ParameterOperation.getParameterTypesInfo(method , classSignature);
    }

    /**
     * get parameter type information by native
     * @param method {@link Method}
     * @return {@link GenericNode} list
     */
    public static List<GenericNode> getParameterTypeInfoByNative(Method method) {
        return ParameterOperation.getParameterTypesInfoByNative(method);
    }

    /**
     * get parameter names by native
     * @param m {@link Method}
     * @return name list
     */
    public static List<String> getParameterNamesByNative(Method m) {
        return ParameterOperation.getMethodParamNamesByNative(m);
    }
    
    /**
     * invoke method
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
