package com.github.andyshao.nio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;

import org.junit.jupiter.api.Test;

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
            assertEquals(reader.read() , ("[Desktop Entry]"));
            assertEquals(reader.read() , ("Version=1.0"));
        }
    }
}
