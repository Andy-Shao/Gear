package com.github.andyshao.nio;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ByteBufferReaderTest {
    public static Path getBigFilePath() throws URISyntaxException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/nio/txt").toURI();
        return Paths.get(uri);
    }

    public static Path getFilePath() throws URISyntaxException {
        URI uri =
            Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/arithmetic/examples")
                .toURI();
        return Paths.get(uri);
    }

    @Test
    public void efficientTest() throws IOException , URISyntaxException {
        long time = 0L;
        try (FileChannel channel = FileChannel.open(ByteBufferReaderTest.getBigFilePath());
            ByteBufferReader reader = new ByteBufferReader(channel);) {
            time = System.currentTimeMillis();
            while (reader.read() != null)
                ;
            time = System.currentTimeMillis() - time;
        }
        System.out.println("BuffereReader read line use " + time + " millis secondes");
    }

    @Test
    public void testReader() throws IOException , URISyntaxException {
        try (FileChannel channel = FileChannel.open(ByteBufferReaderTest.getFilePath());
            ByteBufferReader reader = new ByteBufferReader(channel);) {
            Assert.assertThat(reader.read() , Matchers.is("[Desktop Entry]".getBytes()));
            Assert.assertThat(reader.read() , Matchers.is("Version=1.0".getBytes()));
        }
    }
}
