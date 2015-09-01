package com.github.andyshao.nio;

import java.nio.ByteBuffer;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ByteBufferOperationTest {
    private volatile ByteBuffer buffer;

    @Before
    public void before() {
        this.buffer = ByteBuffer.allocate(1024);
    }

    @Test
    public void testIndexOf() {
        final ByteBuffer buffer = ByteBuffer.wrap("aaannndddyyyaandyyyyaaannnddd".getBytes());
        final byte[] key = "andy".getBytes();
        final int position = ByteBufferOperation.indexOf(buffer , key);
        Assert.assertArrayEquals(key , ByteBufferOperation.getBytes(buffer , position , key.length));
    }

    @Test
    public void testLastIndexOf() {
        final ByteBuffer buffer = ByteBuffer.wrap("aaannndddyyyandyy".getBytes());
        final byte[] key = "andy".getBytes();
        final int position = ByteBufferOperation.lastIndexOf(buffer , key);
        Assert.assertArrayEquals(key , ByteBufferOperation.getBytes(buffer , position , key.length));
    }

    @Test
    public void testusedArray() {
        byte[] before = "Andy-Shao".getBytes();
        this.buffer.put(before);
        this.buffer.flip();

        byte[] after = ByteBufferOperation.usedArray(this.buffer);
        Assert.assertThat(before.length , Matchers.is(after.length));
        Assert.assertThat(new String(after) , Matchers.is("Andy-Shao"));
    }
}
