package com.github.andyshao.nio;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
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
        URI uri = Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/arithmetic/examples").toURI();
        return Paths.get(uri);
    }

    @Test
    public void basicTest() throws IOException {
        final String filePath = "com/github/andyshao/nio/holiday.conf";
        final byte[] spilts = ",".getBytes();
        try (ByteBufferReader bufferReader = new ByteBufferReader(Channels.newChannel(Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)));) {
            bufferReader.setFindSeparatePoint((buffer) -> new BufferReader.SeparatePoint(ByteBufferOperation.indexOf(buffer , spilts)));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("<New Year's Day><2016-01-01>"));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("\n<New Year's Day><2016-01-02>"));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("\n<New Year's Day><2016-01-03>"));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("\n<the Spring Festival><2016-02-07>"));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("\n<the Spring Festival><2016-02-08>"));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("\n<the Spring Festival><2016-02-09>"));
            Assert.assertThat(new String(bufferReader.read()) , Matchers.is("\n<the Spring Festival><2016-02-10>"));
            Assert.assertTrue(bufferReader.read() == null);
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
//            reader.setFindSeparatePoint(new ByteBufferReader.SeparateByBytes(GeneralSystemProperty.LINE_SEPARATOR.value().getBytes()));
            reader.setFindSeparatePoint(new ByteBufferReader.SeparateByBytes("\n".getBytes()));
            Assert.assertThat(reader.read() , Matchers.is("[Desktop Entry]".getBytes()));
            Assert.assertThat(reader.read() , Matchers.is("Version=1.0".getBytes()));
        }
    }
}
