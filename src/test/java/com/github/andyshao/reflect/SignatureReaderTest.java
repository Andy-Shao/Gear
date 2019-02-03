package com.github.andyshao.reflect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import com.github.andyshao.asm.ApiConfs;
import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class SignatureReaderTest {
    private ClassSignature signature = new SignatureDetector(ApiConfs.DEFAULT_ASM_VERSION).getSignature(MyClass.class);
    private SignatureVisitor visitor = new SignatureVisitor(ApiConfs.DEFAULT_ASM_VERSION) {

        @Override
        public void visitFormalTypeParameter(String name) {
            System.out.println(String.format("visitFormalTypeParameter: %s" , name));
        }

        @Override
        public void visitBaseType(char descriptor) {
            System.out.println(String.format("visitBaseType: %s" , descriptor));
        }

        @Override
        public void visitTypeVariable(String name) {
            System.out.println(String.format("visitTypeVariable: %s" , name));
        }

        @Override
        public void visitClassType(String name) {
            System.out.println(String.format("visitClassType: %s" , name));
        }

        @Override
        public void visitInnerClassType(String name) {
            System.out.println(String.format("visitInnerClassType: %s" , name));
        }

        @Override
        public SignatureVisitor visitReturnType() {
            System.out.println("visitReturnType");
            return super.visitReturnType();
        }

        @Override
        public SignatureVisitor visitTypeArgument(char wildcard) {
            System.out.println(String.format("visitTypeArgument: %s", wildcard));
            return super.visitTypeArgument(wildcard);
        }

        @Override
        public SignatureVisitor visitParameterType() {
            System.out.println(String.format("visitParameterType"));
            return super.visitParameterType();
        }

        @Override
        public SignatureVisitor visitClassBound() {
            System.out.println("visitClassBound");
            return super.visitClassBound();
        }

        @Override
        public SignatureVisitor visitInterfaceBound() {
            System.out.println("visitInterfaceBound");
            return super.visitInterfaceBound();
        }

        @Override
        public SignatureVisitor visitSuperclass() {
            System.out.println("visitSuperclass");
            return super.visitSuperclass();
        }

        @Override
        public SignatureVisitor visitInterface() {
            System.out.println("visitInterface");
            return super.visitInterface();
        }

        @Override
        public SignatureVisitor visitExceptionType() {
            System.out.println("visitExceptionType");
            return super.visitExceptionType();
        }

        @Override
        public SignatureVisitor visitArrayType() {
            System.out.println("visitArrayType");
            return super.visitArrayType();
        }

        @Override
        public void visitTypeArgument() {
            System.out.println("visitTypeArgument");
            super.visitTypeArgument();
        }

        @Override
        public void visitEnd() {
            System.out.println("visitEnd");
            super.visitEnd();
        }
        
    };
    
    @Test
    public void testMethod() {
        Method method = MethodOperation.getDeclaredMethod(MyClass.class , "method" , List.class, int.class, Map.class);
        String signatureStr = signature.methodSignatures.get(method);
        SignatureReader reader = new SignatureReader(signatureStr);
        reader.accept(visitor);
    }
    
    @Test
    public void testClass(){
        new SignatureReader(signature.classSignature).accept(visitor);
    }
    
    public class MyClass<T extends Comparable<T>> {
        public CompletionStage<Optional<String>> method(List<List<String>> param, int i, Map<String , Object> map){
            return null;
        }
    }
}
