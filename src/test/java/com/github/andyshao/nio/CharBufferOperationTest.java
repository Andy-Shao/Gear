package com.github.andyshao.nio;

import java.nio.CharBuffer;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CharBufferOperationTest {
    private volatile CharBuffer charBuffer;
    private final char[] cs = "aaannndddyyyaandyyyyaaannndddaaannndddyyyandyy".toCharArray();

    @Before
    public void before() {
        this.charBuffer = CharBuffer.wrap(this.cs);
    }

    @Test
    public void testGetChars() {
        Assert.assertThat(CharBufferOperation.getChars(this.charBuffer , 0 , this.charBuffer.limit()) ,
            Matchers.is(this.cs));
    }

    @Test
    public void testIndexOf() {
        final char[] key = "andy".toCharArray();
        final int position = CharBufferOperation.indexOf(this.charBuffer , key);
        Assert.assertThat(CharBufferOperation.getChars(this.charBuffer , position , key.length) , Matchers.is(key));
    }

    @Test
    public void testLastIndexOf() {
        final char[] key = "andy".toCharArray();
        final int position = CharBufferOperation.lastIndexOf(this.charBuffer , key);
        Assert.assertThat(CharBufferOperation.getChars(this.charBuffer , position , key.length) , Matchers.is(key));
    }

    @Test
    public void testUsedArray() {
        Assert.assertThat(CharBufferOperation.usedArray(this.charBuffer) , Matchers.is(this.cs));
    }
}
