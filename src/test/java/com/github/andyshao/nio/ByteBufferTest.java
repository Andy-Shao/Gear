package com.github.andyshao.nio;

import java.nio.ByteBuffer;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ByteBufferTest {

    @Test
    public void testPut() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bs = "andy".getBytes();
        Assert.assertThat(buffer.position() , Matchers.is(0));
        buffer.put(bs);
        Assert.assertThat(buffer.position() , Matchers.is(bs.length));
    }
}
