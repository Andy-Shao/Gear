package com.github.andyshao.reflect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.github.andyshao.asm.Version;
import com.github.andyshao.lang.ClassAssembly;
import com.github.andyshao.lang.StringOperation;
import com.github.andyshao.reflect.SignatureDetector.ClassSignature;
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

    public static GenericInfo getClassGenericInfo(Class<?> clazz) {
        Generic generic = clazz.getAnnotation(Generic.class);
        if (generic == null) throw new ReflectiveOperationException("Cannot find " + Generic.class);
        GenericInfo genericInfo = new GenericInfo();
        genericInfo.isGeneiric = generic.isGeneric();
        genericInfo.componentTypes = GenericInfo.analyseScript(generic.componentTypes());
        genericInfo.declareType = clazz;
        return genericInfo;
    }

    public static <T> byte[] getSuperClassForInterface(Class<T> interfaceClass , String targetName , Version version) {
        if (!interfaceClass.isInterface()) throw new InstantiationException("Class is not interface");
        final ClassSignature csig = new SignatureDetector(Opcodes.ASM5).getSignature(interfaceClass);
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
        cw.visit(version.getVersion() , Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER , targetName.replace('.' , '/') ,
            classSignature , "java/lang/Object" , new String[] { interfaceClass.getName().replace('.' , '/') });
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
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC , method.getName() , Type.getType(method).getDescriptor() ,
                csig.methodSignatures.get(method) , exceptionDescriptions);
            Class<?> returnType = method.getReturnType();
            mv.visitCode();
            if (int.class.isAssignableFrom(returnType) || byte.class.isAssignableFrom(returnType)
                || char.class.isAssignableFrom(returnType) || short.class.isAssignableFrom(returnType)
                || boolean.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.ICONST_0);
                mv.visitInsn(Opcodes.IRETURN);
                mv.visitMaxs(1 , 1);
            } else if (double.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.DCONST_0);
                mv.visitInsn(Opcodes.DRETURN);
                mv.visitMaxs(2 , 1);
            } else if (float.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitInsn(Opcodes.FRETURN);
                mv.visitMaxs(1 , 1);
            } else if (long.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.LCONST_0);
                mv.visitInsn(Opcodes.LRETURN);
                mv.visitMaxs(2 , 1);
            } else if (void.class.isAssignableFrom(returnType)) {
                mv.visitInsn(Opcodes.RETURN);
                mv.visitMaxs(0 , 1);
            } else {
                mv.visitInsn(Opcodes.ACONST_NULL);
                mv.visitInsn(Opcodes.ARETURN);
                mv.visitMaxs(1 , 1);
            }
            mv.visitEnd();
        }
        cw.visitEnd();
        return cw.toByteArray();
    }

    public static boolean isPrimitiveObject(Class<?> clazz) {
        if (Integer.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz)
            || Character.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)
            || Long.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)
            || Double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)
            || Void.class.isAssignableFrom(clazz)) return true;
        return false;
    }

    public static boolean isPrimitiveType(Class<?> clazz) {
        return clazz.isPrimitive() || ClassOperation.isPrimitiveObject(clazz);
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

    public static <T> T newInstanceForInterface(
        Class<T> interfaceClass , String targetName , boolean isKeep , Version version) throws IOException {
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
