package com.github.andyshao.reflect;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

import com.github.andyshao.asm.TypeOperation;

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
    public static class ClassSignature {
        public Class<?> clazz;
        public String classSignature;
        public final Map<Field , String> fieldSignatures = new HashMap<>();
        public final Map<Class<?> , ClassSignature> innerClassSignatures = new HashMap<>();
        public final Map<Method , String> methodSignatures = new HashMap<>();

        @Override
        public String toString() {
            return "ClassSignature [clazz=" + clazz + ", classSignature=" + classSignature + ", fieldSignatures="
                + fieldSignatures + ", innerClassSignatures=" + innerClassSignatures + ", methodSignatures="
                + methodSignatures + "]";
        }
    }

    private int api;
    private volatile ClassSignature signature = new SignatureDetector.ClassSignature();

    public SignatureDetector(int api) {
        super(api);
        this.api = api;
    }

    public ClassSignature getSignature(Class<?> clazz) {
        ClassReader cr;
        try {
            cr = new ClassReader(clazz.getName());
        } catch (IOException e) {
            throw new ClassNotFoundException(e);
        }
        this.signature.clazz = clazz;
        cr.accept(this , 0);
        return this.signature;
    }

    public void refresh() {
        this.signature = new SignatureDetector.ClassSignature();
    }

    @Override
    public void
        visit(int version , int access , String name , String signature , String superName , String[] interfaces) {
        this.signature.classSignature = signature;
        super.visit(version , access , name , signature , superName , interfaces);
    }

    @Override
    public FieldVisitor visitField(int access , String name , String desc , String signature , Object value) {
        if (signature != null) this.signature.fieldSignatures
            .put(FieldOperation.superGetDeclaredField(this.signature.clazz , name) , signature);
        return super.visitField(access , name , desc , signature , value);
    }

    @Override
    public void visitInnerClass(String name , String outerName , String innerName , int access) {
        Class<?> innerClass = ClassOperation.forName(name.replace('/' , '.'));
        if (!innerClass.equals(this.signature.clazz)) this.signature.innerClassSignatures.put(innerClass ,
            new SignatureDetector(this.api).getSignature(innerClass));
        super.visitInnerClass(name , outerName , innerName , access);
    }

    @Override
    public MethodVisitor visitMethod(int access , String name , String desc , String signature , String[] exceptions) {
        if (signature != null) this.signature.methodSignatures
            .put(TypeOperation.getMethod(desc , name , this.signature.clazz) , signature);
        return super.visitMethod(access , name , desc , signature , exceptions);
    }
}
