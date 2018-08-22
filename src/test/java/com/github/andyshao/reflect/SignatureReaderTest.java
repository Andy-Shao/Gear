package com.github.andyshao.reflect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.junit.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureReader;
import org.objectweb.asm.signature.SignatureVisitor;

import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class SignatureReaderTest {
    @Test
    public void testMethod() {
        ClassSignature signature = new SignatureDetector(Opcodes.ASM6).getSignature(MyClass.class);
        Method method = MethodOperation.getDeclaredMethod(MyClass.class , "method" , List.class, int.class, Map.class);
        String signatureStr = signature.methodSignatures.get(method);
        SignatureReader reader = new SignatureReader(signatureStr);
        SignatureVisitor visitor = new SignatureVisitor(Opcodes.ASM6) {

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
            
        };
        reader.accept(visitor);
    }
    
    public class MyClass {
        public CompletionStage<Optional<String>> method(List<List<String>> param, int i, Map<String , Object> map){
            return null;
        }
    }
}
