package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.andyshao.asm.ApiConfs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;

import com.github.andyshao.reflect.SignatureDetector.ClassSignature;

public class SignatureDetectorTest {
    public class MyClass<T extends MyIterable> {
        public Map<T , Method> myMethod(Collection<? extends T> list) {
            return null;
        }
    }

    public class MyClass2 implements MyInterface {
        @Override
        public void myMethod() {
        }
    }

    public interface MyInterface {
        public void myMethod();
    }

    public class MyIt<T , R> implements Iterable<T> {
        @Override
        public Iterator<T> iterator() {
            return null;
        }
    }

    public class MyIterable implements Iterable<Integer> {
        @Override
        public Iterator<Integer> iterator() {
            return null;
        }
    }

    private volatile SignatureDetector detector;

    @BeforeEach
    public void befor() {
        this.detector = new SignatureDetector(ApiConfs.DEFAULT_ASM_VERSION);
    }

    @Test
    public void testForDefineClass() {
        ClassSignature classSignature = this.detector.getSignature(SignatureDetectorTest.MyClass.class);
        assertEquals(classSignature.classSignature , ("<T:Lcom/github/andyshao/reflect/SignatureDetectorTest$MyIterable;>Ljava/lang/Object;"));
        Method method = MethodOperation.getMethod(SignatureDetectorTest.MyClass.class , "myMethod" , Collection.class);
        assertEquals(classSignature.methodSignatures.get(method) , ("(Ljava/util/Collection<+TT;>;)Ljava/util/Map<TT;Ljava/lang/reflect/Method;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(MyClass2.class);
        assertTrue(classSignature.classSignature == null);
        method = MethodOperation.getMethod(MyClass2.class , "myMethod");
        assertTrue(classSignature.methodSignatures.get(method) == null);
    }

    @Test
    public void testForIterable() throws IOException {
        ClassSignature classSignature = this.detector.getSignature(Iterable.class);
        assertEquals(classSignature.classSignature , ("<T:Ljava/lang/Object;>Ljava/lang/Object;"));
        Method method = MethodOperation.getMethod(Iterable.class , "iterator");
        assertEquals(classSignature.methodSignatures.get(method) , ("()Ljava/util/Iterator<TT;>;"));
        method = MethodOperation.getMethod(Iterable.class , "forEach" , Consumer.class);
        assertEquals(classSignature.methodSignatures.get(method) , ("(Ljava/util/function/Consumer<-TT;>;)V"));
        method = MethodOperation.getMethod(Iterable.class , "spliterator");
        assertEquals(classSignature.methodSignatures.get(method) , ("()Ljava/util/Spliterator<TT;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(SignatureDetectorTest.MyIterable.class);
        assertEquals(classSignature.classSignature , ("Ljava/lang/Object;Ljava/lang/Iterable<Ljava/lang/Integer;>;"));
        method = MethodOperation.getMethod(MyIterable.class , "iterator");
        assertEquals(classSignature.methodSignatures.get(method) , ("()Ljava/util/Iterator<Ljava/lang/Integer;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(MyIterable2.class);
        assertEquals(classSignature.classSignature , ("Ljava/lang/Object;Ljava/lang/Iterable<Ljava/lang/Integer;>;"));
        method = MethodOperation.getMethod(MyIterable2.class , "iterator");
        assertEquals(classSignature.methodSignatures.get(method) , ("()Ljava/util/Iterator<Ljava/lang/Integer;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(SignatureDetectorTest.MyIt.class);
        assertEquals(classSignature.classSignature , ("<T:Ljava/lang/Object;R:Ljava/lang/Object;>Ljava/lang/Object;Ljava/lang/Iterable<TT;>;"));
        method = MethodOperation.getMethod(MyIt.class , "iterator");
        assertEquals(classSignature.methodSignatures.get(method) , ("()Ljava/util/Iterator<TT;>;"));
    }

    @Test
    public void testGetSignature() {
        ClassSignature list = new SignatureDetector(ApiConfs.DEFAULT_ASM_VERSION).getSignature(List.class);
    }
}
