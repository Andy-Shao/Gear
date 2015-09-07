package com.github.andyshao.lang;

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
