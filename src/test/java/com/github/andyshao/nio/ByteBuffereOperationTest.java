package com.github.andyshao.nio;

import java.nio.ByteBuffer;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.andyshao.nio.ByteBuffereOperation;

public class ByteBuffereOperationTest {

    private volatile ByteBuffer buffer;

    @Before
    public void before() {
        this.buffer = ByteBuffer.allocate(1024);
    }

    @Test
    public void testusedArray() {
        byte[] before = "Andy-Shao".getBytes();
        this.buffer.put(before);
        this.buffer.flip();

        byte[] after = ByteBuffereOperation.usedArray(this.buffer);
        Assert.assertThat(before.length , Matchers.is(after.length));
        Assert.assertThat(new String(after) , Matchers.is("Andy-Shao"));
    }
}
