package com.github.andyshao.number;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.lang.number.ByteLevel;
import com.github.andyshao.lang.number.ByteSize;
import com.github.andyshao.lang.number.SimpleByteSize;

public class SimpleByteSizeTest {

    @Test
    public void testCovert() {
        ByteSize byteSize = SimpleByteSize.covertToString("15B");
        System.out.println(byteSize);
        byteSize = SimpleByteSize.covertToString("20MB");
        System.out.println(byteSize);
    }
    
    @Test
    public void testGetSize() {
        ByteSize byteSize = SimpleByteSize.covertToString("1024B");
        Assert.assertThat(byteSize.getSize() , Matchers.is(1024L));
        Assert.assertThat(byteSize.getLevel() , Matchers.is(ByteLevel.BYTE));
        Assert.assertThat(byteSize.getSize(ByteLevel.KB) , Matchers.is(1L));
        
        byteSize = SimpleByteSize.covertToString("4184342528B");
        Assert.assertThat(byteSize.getSize(ByteLevel.MB) , Matchers.is(3990L));
        
        byteSize = SimpleByteSize.covertToString("3990MB");
        Assert.assertThat(byteSize.getSize(ByteLevel.BYTE), Matchers.is(4183818240L));
    }
}
