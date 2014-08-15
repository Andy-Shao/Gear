package com.github.andyshao.util;

import java.nio.ByteBuffer;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ByteBuffereToolsTest {

	private volatile ByteBuffer buffer;

	@Before
	public void before(){
		this.buffer = ByteBuffer.allocate(1024);
	}
	
	@Test
	public void testusedArray(){
		byte[] before = "Andy-Shao".getBytes();
		this.buffer.put(before);
		this.buffer.flip();
		
		byte[] after = ByteBuffereTools.usedArray(this.buffer);
		Assert.assertThat(before.length, Matchers.is(after.length));
		Assert.assertThat(new String(after), Matchers.is("Andy-Shao"));
	}
}
