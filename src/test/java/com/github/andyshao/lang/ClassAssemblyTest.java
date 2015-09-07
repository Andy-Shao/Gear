package com.github.andyshao.lang;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;

import org.junit.Test;

import com.github.andyshao.nio.BufferReader;
import com.github.andyshao.nio.ByteBufferReader;

public class ClassAssemblyTest {
    private static final String CLASS_PATH = "com/github/andyshao/lang/ClassDemo.class";

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

    @Test
    public void loadTest() {
        System.out.println(CLASS_PATH);
        //        MyClassLoader cl = new MyClassLoader("myClassLoader");  
        //        Class<?> clazz = cl.loadClass("classloader.Animal");  
        //        Animal animal=(Animal) clazz.newInstance();  
        //        animal.say();  
    }
}
