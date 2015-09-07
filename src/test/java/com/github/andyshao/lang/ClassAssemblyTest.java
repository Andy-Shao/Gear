package com.github.andyshao.lang;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.util.function.Supplier;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.ByteBufferReader;
import com.github.andyshao.reflect.Reflects;

public class ClassAssemblyTest {
    static class MyClassLoader extends ClassLoader {
        private final String basePath;

        public MyClassLoader(String basePath) {
            this.basePath = basePath;
        }

        public MyClassLoader(String basePath , ClassLoader parent) {
            super(parent);
            this.basePath = basePath;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] data = this.loadClassData(name);
            return this.defineClass(name , data , 0 , data.length);
        }

        private byte[] loadClassData(String name) {
            try (
                ByteBufferReader reader =
                    new ByteBufferReader(Channels.newChannel(new FileInputStream(this.basePath
                        + name.replace("." , "//") + ".class")))) {
                reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
                return reader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final String CLASS_PATH = "com/github/andyshao/lang/ClassDemo.class";

    @Test
    public void loadTest() throws IOException {
        byte[] classInfo = null;
        try (
            InputStream inputStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(ClassAssemblyTest.CLASS_PATH);
            ByteBufferReader reader = new ByteBufferReader(Channels.newChannel(inputStream));) {
            reader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(-1));
            classInfo = reader.read();
        }
        Class<Supplier<String>> clazz =
            ClassAssembly.DEFAULT.<Supplier<String>> assemble(
                ClassAssemblyTest.CLASS_PATH.replace("/" , ".").replace(".class" , "") , classInfo);
        String answer = Reflects.<String> invoked(Reflects.newInstance(clazz) , Reflects.getMethod(clazz , "get"));
        Assert.assertThat(answer , Matchers.is("Hello,World!"));
    }
}
