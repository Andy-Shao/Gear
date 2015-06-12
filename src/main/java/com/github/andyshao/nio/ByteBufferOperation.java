package com.github.andyshao.nio;

import java.nio.ByteBuffer;

/**
 * 
 * Descript:<br>
 * Copyright: Copyright(c) Aug 4, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class ByteBufferOperation {
    public static int indexOf(ByteBuffer buffer , byte... bs) {
        //TODO
        return -1;
    }

    public static int lastIndexOf(ByteBuffer buffer , byte... bs) {
        //TODO
        return -1;
    }

    /**
     * Only return a space which great than buffer.position() and less than
     * buffer.limit()
     * 
     * @param buffer the buffer which has the info
     * @return return the byte array and the info in it
     */
    public static byte[] usedArray(ByteBuffer buffer) {
        byte[] bs = new byte[buffer.limit() - buffer.position()];
        buffer.get(bs);
        return bs;
    }

    private ByteBufferOperation() {
        throw new AssertionError("No support instance " + ByteBufferOperation.class.getName());
    }
}
