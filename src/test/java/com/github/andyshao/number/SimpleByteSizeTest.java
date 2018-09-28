package com.github.andyshao.number;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
        assertEquals(byteSize.getSize() , (1024L));
        assertEquals(byteSize.getLevel() , (ByteLevel.BYTE));
        assertEquals(byteSize.getSize(ByteLevel.KB) , (1L));
        
        byteSize = SimpleByteSize.covertToString("4184342528B");
        assertEquals(byteSize.getSize(ByteLevel.MB) , (3990L));
        
        byteSize = SimpleByteSize.covertToString("3990MB");
        assertEquals(byteSize.getSize(ByteLevel.BYTE), (4183818240L));
    }
}
