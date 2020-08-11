package com.github.andyshao.reflect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.github.andyshao.asm.ApiConfs;
import com.github.andyshao.asm.ClassVisitorOperation;
import com.github.andyshao.asm.Version;
import com.github.andyshao.lang.ClassAssembly;
import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.reflect.SignatureDetector.ClassSignature;
import com.github.andyshao.reflect.annotation.Generic;

import lombok.NonNull;

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
    
    public static <T> Class<T> forType(java.lang.reflect.Type typ) {
    	return forName(typ.getTypeName());
    }

    @Deprecated
    public static GenericInfo getClassGenericInfo(Class<?> clazz) {
        Generic generic = clazz.getAnnotation(Generic.class);
        if (generic == null) throw new ReflectiveOperationException("Cannot find " + Generic.class);
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.isGeneiric = generic.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
        genericInfo.declareType = clazz;
        return genericInfo;
    }
    
    public static GenericNode getSuperClasssGenericInfo(Class<?> clazz) {
    	java.lang.reflect.Type type = clazz.getGenericSuperclass();
    	if(type == null) return null;
    	
    	GenericNode rest = new GenericNode();
    	rest.setParent(null);
    	wrapGenericNode(type, rest);
		return rest; 
    }

	static void wrapGenericNode(@NonNull java.lang.reflect.Type type, @NonNull GenericNode rest) {
		if(type instanceof ParameterizedType) {
    		rest.setGeneiric(true);
    		setDeclareType(rest, type);
    		getGenericInfo((ParameterizedType) type, rest);
    	} else {
    		rest.setGeneiric(false);
    		setDeclareType(rest, type);
    	}
	}
    
    public static Map<Class<?>, GenericNode> getInterfaceGenericInfos(Class<?> clazz) {
    	HashMap<Class<?>, GenericNode> rest = new HashMap<Class<?>, GenericNode>();
    	java.lang.reflect.Type[] typs = clazz.getGenericInterfaces();
    	if(typs.length == 0) return Collections.emptyMap();
    	
    	for(java.lang.reflect.Type typ : typs) {
    		GenericNode genericNode = new GenericNode();
    		genericNode.setParent(null);
    		wrapGenericNode(typ, genericNode);
    		rest.put(genericNode.getDeclareType(), genericNode);
    	}
		return rest;
    }

	static void setDeclareType(@NonNull GenericNode rest,@NonNull java.lang.reflect.Type typ) {
		if(typ instanceof ParameterizedType) {
			rest.setDeclareType(ClassOperation.forType(((ParameterizedType) typ).getRawType()));
		} else {
			try {
				rest.setDeclareType(ClassOperation.forType(typ));
			} catch (ClassNotFoundException e) {
				rest.setTypeVariable(typ.getTypeName());
				rest.setDeclareType(Object.class);
			}
		}
	}
    
    static GenericNode getGenericInfo(@NonNull ParameterizedType parmtrizdTyp, GenericNode parent) {
    	GenericNode rest = new GenericNode();
    	rest.setParent(parent);
    	parent.getComponentTypes().add(rest);
    	for(java.lang.reflect.Type typ : parmtrizdTyp.getActualTypeArguments()) {
    		wrapGenericNode(typ, rest);
    	}
		return rest;
    }

    public static List<Class<?>> getSuperClasses(Class<?> clazz) {
        ArrayList<Class<?>> ret = new ArrayList<>();
        getSuperClasses01(clazz, ret);
        return ret;
    }

    private static void getSuperClasses01(Class<?> clazz, List<Class<?>> bucket) {
        if(clazz.equals(Object.class)) return;
        Class<?> superclass = clazz.getSuperclass();
        bucket.add(superclass);
        getSuperClasses01(superclass, bucket);
    }

    public static <T> byte[] getSuperClassForInterface(Class<T> interfaceClass , String targetName , Version version) {
        if (!interfaceClass.isInterface()) throw new InstantiationException("Class is not interface");
        final ClassSignature csig = new SignatureDetector(ApiConfs.DEFAULT_ASM_VERSION).getSignature(interfaceClass);
        String classSignature = null;
        if (csig.classSignature != null) {
            String tail = StringOperation.replaceAll(csig.classSignature , "Ljava/lang/Object" , "");
            String[] parts = tail.split(";");
            for (String part : parts) {
                part = StringOperation.replaceAll(part , "<" , "");
                part = StringOperation.replaceAll(part , ">" , "");
                part = StringOperation.replaceAll(part , ":" , "");
                if (!part.isEmpty()) tail = StringOperation.replaceFirst(tail , ":" , part);
            }
            tail = "L" + interfaceClass.getName().replace('.' , '/') + tail;
            classSignature = csig.classSignature + tail;
        }
        final ClassWriter cw = new ClassWriter(0);
        cw.visit(version.getVersion() , Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER , targetName.replace('.' , '/') , classSignature , "java/lang/Object" ,
            new String[] { interfaceClass.getName().replace('.' , '/') });
        MethodVisitor mv = null;
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC , "<init>" , "()V" , null , null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD , 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL , "java/lang/Object" , "<init>" , "()V" , false);
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1 , 1);
            mv.visitEnd();
        }
        Method[] methods = interfaceClass.getMethods();
        for (Method method : methods) {
            if (method.isDefault() || Modifier.isStatic(method.getModifiers())) continue;
            Class<?>[] exceptions = method.getExceptionTypes();
            String[] exceptionDescriptions = new String[exceptions.length];
            for (int i = 0 ; i < exceptions.length ; i++)
                exceptionDescriptions[i] = exceptions[i].getName().replace('.' , '/');
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC , method.getName() , Type.getType(method).getDescriptor() , csig.methodSignatures.get(method) , exceptionDescriptions);
            Class<?> returnType = method.getReturnType();
            mv.visitCode();
            if (int.class.isAssignableFrom(returnType) || byte.class.isAssignableFrom(returnType) || char.class.isAssignableFrom(returnType) || short.class.isAssignableFrom(returnType)
                || boolean.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.ICONST_0);
                mv.visitInsn(Opcodes.IRETURN);
                mv.visitMaxs(1 , ClassVisitorOperation.countBasicLocal(method));
            } else if (double.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.DCONST_0);
                mv.visitInsn(Opcodes.DRETURN);
                mv.visitMaxs(2 , ClassVisitorOperation.countBasicLocal(method));
            } else if (float.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitInsn(Opcodes.FRETURN);
                mv.visitMaxs(1 , ClassVisitorOperation.countBasicLocal(method));
            } else if (long.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.LCONST_0);
                mv.visitInsn(Opcodes.LRETURN);
                mv.visitMaxs(2 , ClassVisitorOperation.countBasicLocal(method));
            } else if (void.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.RETURN);
                mv.visitMaxs(0 , ClassVisitorOperation.countBasicLocal(method));
            } else {
                mv.visitInsn(Opcodes.ACONST_NULL);
                mv.visitInsn(Opcodes.ARETURN);
                mv.visitMaxs(1 , ClassVisitorOperation.countBasicLocal(method));
            }
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    public static boolean isPrimitiveObject(Class<?> clazz) {
        if (Integer.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)
            || Long.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)
            || Void.class.isAssignableFrom(clazz)) return true;
        return false;
    }

    public static boolean isPrimitiveType(Class<?> clazz) {
        return clazz.isPrimitive() || ClassOperation.isPrimitiveObject(clazz);
    }

    public static <T> T newInstance(Class<T> clazz) {
        return ConstructorOperation.newInstance(clazz);
    }

    /**
     * @deprecated if args has innerclass. e.g. some of innerclass don't have the class name 
     * which clould bring an {@link NoSuchFieldError}
     * @param clazz class type
     * @param args args values
     * @param <T> class type
     * @return the object you want to build
     */
    @Deprecated
    public static <T> T newInstance(Class<T> clazz , Object... args) {
        Class<?>[] argTypes = new Class<?>[args.length];
        for (int i = 0 ; i < args.length ; i++)
            argTypes[i] = args[i].getClass();
        return ConstructorOperation.newInstance(ConstructorOperation.getConstructor(clazz , argTypes) , args);
    }
    
    public static <T> T newInstance(Constructor<T> constructor, Object...args) {
        return ConstructorOperation.newInstance(constructor , args);
    }

    public static <T> T newInstanceForInterface(Class<T> interfaceClass , String targetName , boolean isKeep , Version version) throws IOException {
        byte[] bs = ClassOperation.getSuperClassForInterface(interfaceClass , targetName , version);
        if (isKeep) {
            String filePath = targetName.replace('.' , '/') + ".class";
            File file = new File(filePath);
            if (file.exists()) file.delete();
            File dir = file.getParentFile();
            if (dir == null) ;
            else if (!dir.exists()) dir.mkdirs();
            try (OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(bs);
                outputStream.flush();
            }
        }
        return ClassOperation.newInstance(ClassAssembly.DEFAULT.assemble(targetName , bs));
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
        if (clazz.getSuperclass() != null) ClassOperation.superGetInterfaces(clazz.getSuperclass() , set);
    }

    private ClassOperation() {
        throw new AssertionError("No support instance " + ClassOperation.class + " for you!");
    }
}
