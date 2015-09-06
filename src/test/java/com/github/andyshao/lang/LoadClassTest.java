package com.github.andyshao.lang;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.nio.ByteBufferReader;

public class LoadClassTest {
    private static final String CLASS_PATH = "com/github/andyshao/lang/ClassDemo.java";

    @Before
    public void before() throws IOException {
        try (
            InputStream inputStream =
                Thread.currentThread().getContextClassLoader().getResourceAsStream(LoadClassTest.CLASS_PATH);
            ReadableByteChannel channel = Channels.newChannel(inputStream);
            ByteBufferReader reader = new ByteBufferReader(channel);) {
            while(reader.hasNext()){
                System.out.println(new String(reader.read()));
            }
        }
    }

    @Test
    public void loadTest() {
    }
}
