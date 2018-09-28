package com.github.andyshao.nio;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ByteBufferOperationTest {
    private volatile ByteBuffer buffer;

    @BeforeEach
    public void before() {
        this.buffer = ByteBuffer.allocate(1024);
    }

    @Test
    public void testIndexOf() {
        final ByteBuffer buffer = ByteBuffer.wrap("aaannndddyyyaandyyyyaaannnddd".getBytes());
        final byte[] key = "andy".getBytes();
        final int position = ByteBufferOperation.indexOf(buffer , key);
        assertArrayEquals(key , ByteBufferOperation.getBytes(buffer , position , key.length));
    }

    @Test
    public void testLastIndexOf() {
        final ByteBuffer buffer = ByteBuffer.wrap("aaannndddyyyandyy".getBytes());
        final byte[] key = "andy".getBytes();
        final int position = ByteBufferOperation.lastIndexOf(buffer , key);
        assertArrayEquals(key , ByteBufferOperation.getBytes(buffer , position , key.length));
    }

    @Test
    public void testusedArray() {
        byte[] before = "Andy-Shao".getBytes();
        this.buffer.put(before);
        this.buffer.flip();

        byte[] after = ByteBufferOperation.usedArray(this.buffer);
        assertEquals(before.length , (after.length));
        assertEquals(new String(after) , ("Andy-Shao"));
    }
}
