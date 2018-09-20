package com.github.andyshao.nio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class StringBufferReaderTest {
    @Test
    public void effeicientTest() throws IOException , URISyntaxException {
        long time = 0L;
        try (FileChannel channel = FileChannel.open(ByteBufferReaderTest.getBigFilePath()); StringBufferReader reader = new StringBufferReader(channel);) {
            time = System.currentTimeMillis();
            while (reader.hasNext())
                reader.read();
            time = System.currentTimeMillis() - time;
        }
        System.out.println(StringBufferReader.class + " read line use " + time + " millis secondes");
    }

    @Test
    public void testReader() throws IOException , URISyntaxException {
        try (FileChannel channel = FileChannel.open(ByteBufferReaderTest.getFilePath()); StringBufferReader reader = new StringBufferReader(channel);) {
//            reader.setFindSeparatePoint(new StringBufferReader.SeparateByStr(GeneralSystemProperty.LINE_SEPARATOR.value()));
            reader.setFindSeparatePoint(new StringBufferReader.SeparateByStr("\n"));
            Assert.assertThat(reader.read() , Matchers.is("[Desktop Entry]"));
            Assert.assertThat(reader.read() , Matchers.is("Version=1.0"));
        }
    }
}
