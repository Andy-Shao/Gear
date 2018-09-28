package com.github.andyshao.nio;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class ByteBufferReaderTest {
    public static Path getBigFilePath() throws URISyntaxException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/nio/txt").toURI();
        return Paths.get(uri);
    }

    public static Path getFilePath() throws URISyntaxException {
        URI uri = Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/arithmetic/examples").toURI();
        return Paths.get(uri);
    }

    @Test
    public void basicTest() throws IOException {
        final String filePath = "com/github/andyshao/nio/holiday.conf";
        final byte[] spilts = ",".getBytes();
        try (ByteBufferReader bufferReader = new ByteBufferReader(Channels.newChannel(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)));) {
            bufferReader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(ByteBufferOperation.indexOf(buffer , spilts)));
            assertEquals(new String(bufferReader.read()) , "<New Year's Day><2016-01-01>");
            assertEquals(new String(bufferReader.read()) , "\n<New Year's Day><2016-01-02>");
            assertEquals(new String(bufferReader.read()) , "\n<New Year's Day><2016-01-03>");
            assertEquals(new String(bufferReader.read()) , "\n<the Spring Festival><2016-02-07>");
            assertEquals(new String(bufferReader.read()) , "\n<the Spring Festival><2016-02-08>");
            assertEquals(new String(bufferReader.read()) , "\n<the Spring Festival><2016-02-09>");
            assertEquals(new String(bufferReader.read()) , "\n<the Spring Festival><2016-02-10>");
            assertNull(bufferReader.read());
        }
    }

    @Test
    public void efficientTest() throws IOException , URISyntaxException {
        long time = 0L;
        try (FileChannel channel = FileChannel.open(ByteBufferReaderTest.getBigFilePath()); ByteBufferReader reader = new ByteBufferReader(channel);) {
            time = System.currentTimeMillis();
            while (reader.hasNext())
                reader.read();
            time = System.currentTimeMillis() - time;
        }
        System.out.println(ByteBufferReader.class + " read line use " + time + " millis secondes");
    }

    @Test
    public void testReader() throws IOException , URISyntaxException {
        try (FileChannel channel = FileChannel.open(ByteBufferReaderTest.getFilePath()); ByteBufferReader reader = new ByteBufferReader(channel);) {
            reader.setFindSeparatePoint(new ByteBufferReader.SeparateByBytes("\n".getBytes()));
            assertArrayEquals(reader.read() , "[Desktop Entry]".getBytes());
            assertArrayEquals(reader.read() , "Version=1.0".getBytes());
        }
    }
}
