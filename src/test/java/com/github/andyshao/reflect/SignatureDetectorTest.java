package com.github.andyshao.reflect;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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

    public class MyIt<T, R> implements Iterable<T> {
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

    @Before
    public void befor() {
        this.detector = new SignatureDetector(Opcodes.ASM5);
    }

    @Test
    public void testForDefineClass() {
        ClassSignature classSignature = this.detector.getSignature(SignatureDetectorTest.MyClass.class);
        Assert.assertThat(classSignature.classSignature ,
            Matchers.is("<T:Lcom/github/andyshao/reflect/SignatureDetectorTest$MyIterable;>Ljava/lang/Object;"));
        Method method = MethodOperation.getMethod(SignatureDetectorTest.MyClass.class , "myMethod" , Collection.class);
        Assert.assertThat(classSignature.methodSignatures.get(method) ,
            Matchers.is("(Ljava/util/Collection<+TT;>;)Ljava/util/Map<TT;Ljava/lang/reflect/Method;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(MyClass2.class);
        Assert.assertTrue(classSignature.classSignature == null);
        method = MethodOperation.getMethod(MyClass2.class , "myMethod");
        Assert.assertTrue(classSignature.methodSignatures.get(method) == null);
    }

    @Test
    public void testForIterable() throws IOException {
        ClassSignature classSignature = this.detector.getSignature(Iterable.class);
        Assert.assertThat(classSignature.classSignature , Matchers.is("<T:Ljava/lang/Object;>Ljava/lang/Object;"));
        Method method = MethodOperation.getMethod(Iterable.class , "iterator");
        Assert.assertThat(classSignature.methodSignatures.get(method) , Matchers.is("()Ljava/util/Iterator<TT;>;"));
        method = MethodOperation.getMethod(Iterable.class , "forEach" , Consumer.class);
        Assert.assertThat(classSignature.methodSignatures.get(method) ,
            Matchers.is("(Ljava/util/function/Consumer<-TT;>;)V"));
        method = MethodOperation.getMethod(Iterable.class , "spliterator");
        Assert.assertThat(classSignature.methodSignatures.get(method) , Matchers.is("()Ljava/util/Spliterator<TT;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(SignatureDetectorTest.MyIterable.class);
        Assert.assertThat(classSignature.classSignature ,
            Matchers.is("Ljava/lang/Object;Ljava/lang/Iterable<Ljava/lang/Integer;>;"));
        method = MethodOperation.getMethod(MyIterable.class , "iterator");
        Assert.assertThat(classSignature.methodSignatures.get(method) ,
            Matchers.is("()Ljava/util/Iterator<Ljava/lang/Integer;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(MyIterable2.class);
        Assert.assertThat(classSignature.classSignature ,
            Matchers.is("Ljava/lang/Object;Ljava/lang/Iterable<Ljava/lang/Integer;>;"));
        method = MethodOperation.getMethod(MyIterable2.class , "iterator");
        Assert.assertThat(classSignature.methodSignatures.get(method) ,
            Matchers.is("()Ljava/util/Iterator<Ljava/lang/Integer;>;"));

        this.detector.refresh();
        classSignature = this.detector.getSignature(SignatureDetectorTest.MyIt.class);
        Assert.assertThat(classSignature.classSignature ,
            Matchers.is("<T:Ljava/lang/Object;R:Ljava/lang/Object;>Ljava/lang/Object;Ljava/lang/Iterable<TT;>;"));
        method = MethodOperation.getMethod(MyIt.class , "iterator");
        Assert.assertThat(classSignature.methodSignatures.get(method) , Matchers.is("()Ljava/util/Iterator<TT;>;"));
    }
}
