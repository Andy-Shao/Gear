package com.github.andyshao.nio;

import java.nio.ByteBuffer;

import com.github.andyshao.reflect.ArrayOperation;

/**
 * 
 * Descript:<br>
 * Copyright: Copyright(c) Aug 4, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ByteBuffereOperation {
    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()
     * 
     * @param buffer the buffer which has the info
     * @return return the byte array and the info in it
     */
    public static byte[] usedArray(ByteBuffer buffer) {
        return ArrayOperation.splitArray(buffer.array() , buffer.position() , buffer.limit());
    }

    private ByteBuffereOperation() {
        throw new AssertionError("No support instance " + ByteBuffereOperation.class.getName());
    }
}
