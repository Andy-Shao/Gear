package com.github.andyshao.nio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class ByteBufferTest {

    @Test
    public void testPut() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bs = "andy".getBytes();
        assertEquals(buffer.position() , (0));
        buffer.put(bs);
        assertEquals(buffer.position() , (bs.length));
    }
}
