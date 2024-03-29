package com.github.andyshao.reflect;

import com.github.andyshao.asm.TypeOperation;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 17, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class SignatureDetector extends ClassVisitor {
    /**
     * Class signature
     */
    public static class ClassSignature {
        /**class signature*/
        public String classSignature;
        /**clazz*/
        public Class<?> clazz;
        /**field signatures*/
        public final Map<Field , String> fieldSignatures = new HashMap<>();
        /**inner class signatures*/
        public final Map<Class<?> , ClassSignature> innerClassSignatures = new HashMap<>();
        /**method signature*/
        public final Map<Method , String> methodSignatures = new HashMap<>();

        @Override
        public String toString() {
            return "ClassSignature [clazz=" + this.clazz + ", classSignature=" + this.classSignature + ", fieldSignatures=" + this.fieldSignatures + ", innerClassSignatures="
                + this.innerClassSignatures + ", methodSignatures=" + this.methodSignatures + "]";
        }
    }

    private int api;
    private volatile ClassSignature signature = new SignatureDetector.ClassSignature();

    /**
     * build {@link SignatureDetector}
     * @param api api
     */
    public SignatureDetector(int api) {
        super(api);
        this.api = api;
    }

    /**
     * get signature
     * @param clazz {@link Class}
     * @return {@link ClassSignature}
     */
    public ClassSignature getSignature(Class<?> clazz) {
        ClassReader cr;
        try {
            cr = new ClassReader(clazz.getName());
        } catch (IOException e) {
            throw new ClassNotFoundException(clazz.getName(), e);
        }
        this.signature.clazz = clazz;
        cr.accept(this , 0);
        return this.signature;
    }

    /**
     * get signature
     * @param clazz class type
     * @param inputStream class file input stream (Sometime, the inputStream is hard to get)
     * @return return type
     */
    public ClassSignature getSignature(Class<?> clazz, InputStream inputStream) {
        ClassReader cr;
        try {
            cr = new ClassReader(inputStream);
        } catch (IOException e) {
            throw new ClassNotFoundException(e);
        }
        this.signature.clazz = clazz;
        cr.accept(this, 0);
        return this.signature;
    }

    /**
     * refresh
     */
    public void refresh() {
        this.signature = new SignatureDetector.ClassSignature();
    }

    @Override
    public void visit(int version , int access , String name , String signature , String superName , String[] interfaces) {
        this.signature.classSignature = signature;
        super.visit(version , access , name , signature , superName , interfaces);
    }

    @Override
    public FieldVisitor visitField(int access , String name , String desc , String signature , Object value) {
        if (signature != null) this.signature.fieldSignatures.put(FieldOperation.superGetDeclaredField(this.signature.clazz , name) , signature);
        return super.visitField(access , name , desc , signature , value);
    }

    @Override
    public void visitInnerClass(String name , String outerName , String innerName , int access) {
        Class<?> innerClass = ClassOperation.forName(name.replace('/' , '.'));
        if (isTrueInnerClass(innerClass)) this.signature.innerClassSignatures.put(innerClass , new SignatureDetector(this.api).getSignature(innerClass));
        super.visitInnerClass(name , outerName , innerName , access);
    }

    private boolean isTrueInnerClass(Class<?> innerClass) {
//        return !innerClass.equals(this.signature.clazz);
        return this.signature.clazz.equals(innerClass.getSuperclass());
    }

    @Override
    public MethodVisitor visitMethod(int access , String name , String desc , String signature , String[] exceptions) {
        if (signature != null) {
            if(Objects.equals(name , "<init>")); // ignore
            else this.signature.methodSignatures.put(TypeOperation.getMethod(desc , name , this.signature.clazz) , signature);
        }
        return super.visitMethod(access , name , desc , signature , exceptions);
    }
}
