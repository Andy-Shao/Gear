package com.github.andyshao.nio;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.nio.CharBuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CharBufferOperationTest {
    private volatile CharBuffer charBuffer;
    private final char[] cs = "aaannndddyyyaandyyyyaaannndddaaannndddyyyandyy".toCharArray();

    @BeforeEach
    public void before() {
        this.charBuffer = CharBuffer.wrap(this.cs);
    }

    @Test
    public void testGetChars() {
        assertArrayEquals(CharBufferOperation.getChars(this.charBuffer , 0 , this.charBuffer.limit()) , (this.cs));
    }

    @Test
    public void testIndexOf() {
        final char[] key = "andy".toCharArray();
        final int position = CharBufferOperation.indexOf(this.charBuffer , key);
        assertArrayEquals(CharBufferOperation.getChars(this.charBuffer , position , key.length) , (key));
    }

    @Test
    public void testLastIndexOf() {
        final char[] key = "andy".toCharArray();
        final int position = CharBufferOperation.lastIndexOf(this.charBuffer , key);
        assertArrayEquals(CharBufferOperation.getChars(this.charBuffer , position , key.length) , (key));
    }

    @Test
    public void testUsedArray() {
        assertArrayEquals(CharBufferOperation.usedArray(this.charBuffer) , (this.cs));
    }
}
