package com.github.andyshao.test.arithmetic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class HuffmanCompressTest {

    private volatile byte[] context;
    private volatile HuffmanCompress huffmanCompress;

    @Before
    public void before() throws IOException , URISyntaxException {
        Path path = Paths.get(Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/arithmetic/examples").toURI());
        this.context = Files.readAllBytes(path);
        this.huffmanCompress = new HuffmanCompress();
    }

    @Test
    public void testCompress() {
        //        byte[] compressed = this.huffmanCompress.compress(this.context);
        this.context.getClass();
        this.huffmanCompress.getClass();
    }
}
