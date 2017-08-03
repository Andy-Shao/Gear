package com.github.andyshao.number;

import org.junit.Test;

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
}
