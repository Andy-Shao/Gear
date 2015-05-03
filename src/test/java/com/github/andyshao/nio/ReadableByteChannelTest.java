package com.github.andyshao.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ReadableByteChannelTest {

    private static final URL RESOURCES = Thread.currentThread().getContextClassLoader()
        .getResource("com/github/andyshao/arithmetic/examples");

    @Test
    public void readFixTest() throws FileNotFoundException , IOException , URISyntaxException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try (
            ReadableByteChannel channel =
                Channels.newChannel(new FileInputStream(new File(ReadableByteChannelTest.RESOURCES.toURI())))) {
            while (buffer.position() < 4)
                channel.read(buffer);
            buffer.flip();
            byte[] bs = new byte[4];
            buffer.get(bs);
            Assert.assertThat(new String(bs) , Matchers.is("[Des"));
            bs = new byte[buffer.limit() - buffer.position()];
            buffer.get(bs);
        }
    }
}
